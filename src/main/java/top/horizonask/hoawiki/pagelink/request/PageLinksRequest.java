package top.horizonask.hoawiki.pagelink.request;

import lombok.Data;
import top.horizonask.hoawiki.pagelink.entity.PageLink;

import java.util.List;

/**
 * @description:
 * @author: Yanbo Han
 * @time: 2022/1/2 18:04
 */
@Data
public class PageLinksRequest {

    private List<PageLink> pageLinkList;
}
