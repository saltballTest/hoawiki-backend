package top.horizonask.hoawiki.ossstorage.entity;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import top.horizonask.hoawiki.common.ApiStatus;
import top.horizonask.hoawiki.common.ResponseUtils;
import top.horizonask.hoawiki.ossstorage.config.OssConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @description:
 * @time: 2022/2/11 3:58
 */

@Slf4j
@Service
public class wxOssUploadResponseService {

    private final OssConfig ossConfig;

    public wxOssUploadResponseService(OssConfig ossConfig) {
        this.ossConfig = ossConfig;
    }

    public ResponseUtils getOssUploadResponse(String cloudpath) throws URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.weixin.qq.com")
                .setPath("/tcb/uploadfile")
                .build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Content-Type","application/json");
        JSONObject requestBody = JSONUtil.createObj();
        requestBody.set("path",cloudpath).set("env", ossConfig.getWeChatEnv());
        log.info(ossConfig.getWeChatEnv());
        try{
            httpPost.setEntity(new StringEntity(String.valueOf(requestBody), ContentType.create("application/json", "utf-8")));
            log.info(String.valueOf(EntityUtils.toString(httpPost.getEntity())));
            HttpResponse response = httpClient.execute(httpPost);
            if(response!=null && response.getStatusLine().getStatusCode() == 200){
                ResponseUtils res = ResponseUtils.success();
                res.data("ossupload", JSONUtil.parse(EntityUtils.toString(response.getEntity())));
                return res;
            }
            else{
                assert response != null;
                return ResponseUtils.fail(ApiStatus.API_RESPONSE_ERROR, String.valueOf(response.getStatusLine().getStatusCode()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseUtils.fail(ApiStatus.API_RESPONSE_ERROR);
    }
}
