package top.horizonask.hoawiki.pagelink.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import top.horizonask.hoawiki.common.ApiStatus;
import top.horizonask.hoawiki.pagelink.entity.PageLink;

import java.util.List;

/**
 * @description:
 * @time: 2022/1/24 21:27
 */

public interface PageLinkService extends IService<PageLink> {

    /**
     * <b>Get Page link tos</b>
     * <p><b>pageId</b>->?</p>
     *
     * @param pageId page id to get page links.
     * @return java.util.List<top.horizonask.hoawiki.pagelink.entity.PageLink>
     */
    List<PageLink> getPageLinkFromId(Long pageId);

    /**
     * <b>Get Pages be linked</b>
     * <p>?-><b>pageId</b></p>
     *
     * @param pageId page id to get page links.
     * @return java.util.List<top.horizonask.hoawiki.pagelink.entity.PageLink>
     */
    List<PageLink> getPageLinkToId(Long pageId);

    /**
     * <b>Get Page link tos</b>
     * <p><b>pageIds</b>->?</p>
     *
     * @param pageIds page id to get page links.
     * @return java.util.List<top.horizonask.hoawiki.pagelink.entity.PageLink>
     */
    List<PageLink> getPageLinkFromIds(List<Long> pageIds);

    /**
     * <b>Get Pages be linked</b>
     * <p>?-><b>pageIds</b></p>
     *
     * @param pageIds page id to get page links.
     * @return java.util.List<top.horizonask.hoawiki.pagelink.entity.PageLink>
     */
    List<PageLink> getPageLinkToIds(List<Long> pageIds);

    /**
     * <b>Create new PageLinks with multiple inputs.</b>
     *
     * @param newPageLinkList List of PageLink
     * @return top.horizonask.hoawiki.common.ApiStatus
     */
    @Transactional
    ApiStatus createPageLink(List<PageLink> newPageLinkList);
}
