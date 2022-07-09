package top.horizonask.hoawiki.pagelink.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.horizonask.hoawiki.common.ApiStatus;
import top.horizonask.hoawiki.common.ResponseUtils;
import top.horizonask.hoawiki.common.ValidateUtils;
import top.horizonask.hoawiki.pagelink.entity.PageLink;
import top.horizonask.hoawiki.pagelink.request.PageLinksRequest;
import top.horizonask.hoawiki.pagelink.service.Impl.PageLinkServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @time: 2022/1/25 1:56
 */
@Slf4j
@RestController
@RequestMapping("/pagelinks")
public class PagelinkController {
    final PageLinkServiceImpl pageLinkServiceImpl;

    public PagelinkController(PageLinkServiceImpl pageLinkServiceImpl) {
        this.pageLinkServiceImpl = pageLinkServiceImpl;
    }

    /**
     * <p>Recursively getting page link relationships</p>
     * @param pageId pageId of page
     * @param depth Iteration depth
     * @return org.springframework.http.ResponseEntity<cn.hutool.json.JSONObject>
     */
    @GetMapping("")
    public ResponseEntity<JSONObject> getPageLinkOf(@RequestParam String pageId, @RequestParam Integer depth) {
        if (ValidateUtils.wrongRequestPageId(pageId)) {
            return ResponseUtils.fail(ApiStatus.API_RESPONSE_PARAM_BAD).message("页面ID格式错误").toResponseEntity();
        }
        if (depth>20 || depth<0){
            return ResponseUtils.fail(ApiStatus.API_RESPONSE_PARAM_BAD).message("Link深度信息错误").toResponseEntity();
        }
        int idDepth = 0;
        ArrayList<Long> idList = new ArrayList<>();
        idList.add(Long.valueOf(pageId));
        Map<Long,PageLink> pageLinkMap = MapUtil.newHashMap();
        Map<Integer,List<Long>> depNodeMap = MapUtil.newHashMap();
        depNodeMap.put(0, Collections.singletonList(Long.parseLong(pageId)));
        while(idDepth<depth){
            idDepth+=1;
            List<PageLink> tmpPageLinkListTo = pageLinkServiceImpl.getPageLinkToIds(idList);
            tmpPageLinkListTo.forEach((pageLink -> pageLinkMap.put(pageLink.getPageLinkId(),pageLink)));
            List<PageLink> tmpPageLinkListFrom = pageLinkServiceImpl.getPageLinkFromIds(idList);
            tmpPageLinkListFrom.forEach((pageLink -> pageLinkMap.put(pageLink.getPageLinkId(),pageLink)));
            idList.addAll(tmpPageLinkListTo.stream().map(PageLink::getPageFrom).collect(Collectors.toList()));
            idList.addAll(tmpPageLinkListFrom.stream().map(PageLink::getPageTo).collect(Collectors.toList()));
        }
        ResponseUtils res = ResponseUtils.success();
        res.data("items", pageLinkMap.values());
        return res.toResponseEntity();
    }

    @GetMapping("/from")
    public ResponseEntity<JSONObject> getPageLinkFrom(@RequestParam String pageId) {
        if (ValidateUtils.wrongRequestPageId(pageId)) {
            return ResponseUtils.fail(ApiStatus.API_RESPONSE_PARAM_BAD).message("页面ID格式错误").toResponseEntity();
        }
        List<PageLink> pageLinkList = pageLinkServiceImpl.getPageLinkFromId(Long.valueOf(pageId));
        ResponseUtils res = ResponseUtils.success();
        res.data("items", pageLinkList);
        return res.toResponseEntity();
    }

    @GetMapping("/to")
    public ResponseEntity<JSONObject> getPageLinkTo(@RequestParam String pageId) {
        if (ValidateUtils.wrongRequestPageId(pageId)) {
            return ResponseUtils.fail(ApiStatus.API_RESPONSE_PARAM_BAD).message("页面ID格式错误").toResponseEntity();
        }
        List<PageLink> pageLinkList = pageLinkServiceImpl.getPageLinkToId(Long.valueOf(pageId));
        ResponseUtils res = ResponseUtils.success();
        res.data("items", pageLinkList);
        return res.toResponseEntity();
    }

    @PostMapping("")
    public ResponseEntity<JSONObject> createPageLinks(@RequestBody PageLinksRequest pageLinksRequests) {
        ApiStatus createPageLinkStatus = ApiStatus.API_RESPONSE_ERROR;
        try {
            createPageLinkStatus = pageLinkServiceImpl.createPageLink(pageLinksRequests.getPageLinkList());
        } catch (Exception e) {
            return ResponseUtils.fail(createPageLinkStatus)
                    .toResponseEntity();
        }
        if (createPageLinkStatus == ApiStatus.API_RESPONSE_CONTENT_CREATED) {
            ResponseUtils res = ResponseUtils.success();
            return res.toResponseEntity();
        } else {
            return ResponseUtils.fail(createPageLinkStatus)
                    .toResponseEntity();
        }
    }

}
