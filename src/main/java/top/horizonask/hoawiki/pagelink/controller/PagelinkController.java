package top.horizonask.hoawiki.pagelink.controller;

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

import java.util.List;

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
