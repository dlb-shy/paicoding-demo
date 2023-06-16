package com.sun.job;

import com.alibaba.fastjson.JSONObject;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

/**
 * @author sunshine
 * @date 2023/4/24 下午7:36
 */
@DisallowConcurrentExecution
public class HttpRemoteJob implements Job {
    //日志
    private static final Logger log = LoggerFactory.getLogger(HttpRemoteJob.class);

    @Override
    public void execute(JobExecutionContext context)throws JobExecutionException {
        //用于发送网络请求
        HttpURLConnection connection = null;
        //用于接收请求返回的结果
        BufferedReader bufferedReader = null;
        //获取任务Description述,之前我们把接口请求的信息放在Description里面了
        String jsonParams = context.getJobDetail().getDescription();
        if (StringUtils.isEmpty(jsonParams)){
            return;
        }
        //解析Description,获取请求url
        JSONObject jsonObj= (JSONObject) JSONObject.parse(jsonParams);
        String callUrl = jsonObj.getString("callUrl");
        if(StringUtils.isEmpty(callUrl)) {
            return;
        }

        try {
            //编辑请求信息
            URL realUrl = new URL(callUrl);
            connection = (HttpURLConnection) realUrl.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(3 * 1000);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept-Charset", "application/json;charset=UTF-8");
            //发送请求
            connection.connect();

            //获取请求返回的状态吗
            int statusCode = connection.getResponseCode();
            if (statusCode != 200){
                //请求失败抛出异常
                throw new RuntimeException("Http Request StatusCode(" + statusCode + ") Invalid.");
            }
            //如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            // 从流中读取响应信息
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String responseMsg = stringBuilder.toString();
            log.info(responseMsg);

        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            //关闭流与请求连接
            try {
                if (Objects.nonNull(bufferedReader)){
                    bufferedReader.close();
                }
                if (Objects.nonNull(connection)){
                    connection.disconnect();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

}
