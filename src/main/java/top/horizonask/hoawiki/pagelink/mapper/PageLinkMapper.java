package top.horizonask.hoawiki.pagelink.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.horizonask.hoawiki.pagelink.entity.PageLink;

import java.util.List;

@Mapper
public interface PageLinkMapper extends BaseMapper<PageLink> {

    /**
     * <p>Get page link by page from.</p>
     * <p>Means <b>pageId</b>->?</p>
     *
     * @param pageId pageId from
     * @return java.util.List<top.horizonask.hoawiki.pagelink.entity.PageLink>
     */
    @Select("SELECT * " +
            "FROM page_links " +
            "WHERE page_from=#{pageId}")
    List<PageLink> getPageLinkFromId(@Param("pageId") Long pageId);

    /**
     * <p>Get page be linked from.</p>
     * <p>Means ?-><b>pageId</b></p>
     *
     * @param pageId pageId to
     * @return java.util.List<top.horizonask.hoawiki.pagelink.entity.PageLink>
     */
    @Select("SELECT * " +
            "FROM page_links " +
            "WHERE page_to=#{pageId}")
    List<PageLink> getPageLinkToId(@Param("pageId") Long pageId);
}
