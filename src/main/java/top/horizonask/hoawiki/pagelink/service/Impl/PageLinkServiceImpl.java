package top.horizonask.hoawiki.pagelink.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.horizonask.hoawiki.common.ApiStatus;
import top.horizonask.hoawiki.pagelink.entity.PageLink;
import top.horizonask.hoawiki.pagelink.mapper.PageLinkMapper;
import top.horizonask.hoawiki.pagelink.service.PageLinkService;

import java.util.List;

@Service
public class PageLinkServiceImpl extends ServiceImpl<PageLinkMapper, PageLink> implements PageLinkService {

    private final PageLinkMapper pageLinkMapper;

    public PageLinkServiceImpl(PageLinkMapper pageLinkMapper) {

        this.pageLinkMapper = pageLinkMapper;
    }

    /**
     * <b>Get Page link tos</b>
     * <p><b>pageId</b>->?</p>
     *
     * @param pageId page id to get page links.
     * @return java.util.List<java.lang.Long>
     */
    @Override
    public List<PageLink> getPageLinkFromId(Long pageId) {
        return pageLinkMapper.getPageLinkFromId(pageId);
    }

    /**
     * <b>Get Pages be linked</b>
     * <p>?-><b>pageId</b></p>
     *
     * @param pageId page id to get page links.
     * @return java.util.List<java.lang.Long>
     */
    @Override
    public List<PageLink> getPageLinkToId(Long pageId) {
        return pageLinkMapper.getPageLinkToId(pageId);
    }

    /**
     * <b>Create new PageLinks with multiple inputs.</b>
     *
     * @param newPageLinkList List of PageLink
     * @return top.horizonask.hoawiki.common.ApiStatus
     */
    @Override
    @Transactional
    public ApiStatus createPageLink(List<PageLink> newPageLinkList) {
        int affectedCountPageContent = 0;
        if (newPageLinkList.size() == 1) {
            affectedCountPageContent = pageLinkMapper.insert(newPageLinkList.get(0));
        } else if (newPageLinkList.size() >= 1) {
            for (PageLink newPageLink : newPageLinkList) {
                if (pageLinkMapper.insert(newPageLink) == 1) {
                    affectedCountPageContent += 1;
                }
            }
        }

        if (affectedCountPageContent == newPageLinkList.size()) {
            return ApiStatus.API_RESPONSE_CONTENT_CREATED;
        } else {
            return ApiStatus.API_RESPONSE_DATABASE_ERROR;
        }
    }
}
