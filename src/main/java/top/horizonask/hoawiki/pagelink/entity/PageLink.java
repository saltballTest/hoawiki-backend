package top.horizonask.hoawiki.pagelink.entity;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: ConceptPage entity stores page item data.
 * @author: Yanbo Han
 * @time: 2022/1/23 23:26
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "page_links")
public class PageLink extends Model<PageLink> {
    @TableId(value = "page_link_id", type = IdType.AUTO)
    private Long pageLinkId;

    @TableField(value = "page_from")
    private Long pageFrom;

    @TableField(value = "page_to")
    private Long pageTo;


    public JSONObject getJson() {
        JSONObject data = JSONUtil.createObj();
        data.set("pageLink_id", this.getPageLinkId());
        data.set("page_from", this.getPageFrom());
        data.set("page_to", this.getPageTo());
        return data;
    }
}
