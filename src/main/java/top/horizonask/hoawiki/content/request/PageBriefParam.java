package top.horizonask.hoawiki.content.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @description:
 * @time: 2022/1/25 2:20
 */

@Data
public class PageBriefParam {
    private List<Long> pageIds;
}
