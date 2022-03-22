package top.horizonask.hoawiki.ossstorage;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.horizonask.hoawiki.common.ApiStatus;
import top.horizonask.hoawiki.common.ResponseUtils;
import top.horizonask.hoawiki.ossstorage.entity.wxOssUploadResponseService;

import java.net.URISyntaxException;

/**
 * @description:
 * @time: 2022/2/11 3:51
 */

@Slf4j
@RestController
@RequestMapping("/upload")
public class OssController {

    private final wxOssUploadResponseService wxOssUploadResponseService;

    public OssController(wxOssUploadResponseService wxOssUploadResponseService) {
        this.wxOssUploadResponseService = wxOssUploadResponseService;
    }

    @GetMapping("/file")
    public ResponseEntity<JSONObject> getUploadApi(@RequestParam String path){
        try {
            return wxOssUploadResponseService.getOssUploadResponse(path).toResponseEntity();
        }
        catch(URISyntaxException ex){
            return ResponseUtils.fail(ApiStatus.API_RESPONSE_ERROR,ex.getMessage()).toResponseEntity();
        }
    }
}
