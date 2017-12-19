package com.wzc.tencent.task;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzc.SSMCore.domain.TencentDomainService;

@Component("myTask")
public class MysiteTask {

	@Autowired
	private TencentDomainService tencentDomainService;

	//@Scheduled(cron = "0 0 0/1 * * ?")
	@Scheduled(cron = "0/10 * * * * ?")
	public void ChangeIpTask() {
		String newIp = IpUtil.getIp();
		Boolean result = false;
		if (newIp == null || newIp.equals("")) {
			return;
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("Action", "RecordList");
		JSONArray resultJson = JSON.parseObject(tencentDomainService.get(param)).getJSONObject("data")
				.getJSONArray("records");
		if (resultJson.isEmpty()) {
			try {
				param.clear();
				param.put("Action", "AddDomainRecord");
				param.put("subDomain", "@");
				param.put("recordType", "A");
				param.put("value", newIp);
				param.put("recordLine", "默认");
				String data = tencentDomainService.get(param);
				if (JSON.parseObject(data).getString("code").equals("0")) {
					result = true;
				}			
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return;
		}
		int i = 0;
		while (i < resultJson.size()) {
			try {
				JSONObject object = resultJson.getJSONObject(i);
				if (object.isEmpty()) {
					break;
				}
				i++;
				String oldIp = object.getString("value");
				String recordId = object.getString("id");
				String subDomain = object.getString("name");
				String type = object.getString("type");
				if (newIp != null && !newIp.equals(oldIp)) {
					param.clear();
					param.put("Action", "RecordModify");
					param.put("recordId", recordId);
					param.put("subDomain", subDomain);
					param.put("recordType", type);
					param.put("value", newIp);
					param.put("recordLine", "默认");
					String data = tencentDomainService.get(param);
					if (!JSON.parseObject(data).getString("code").equals("0")) {
						result = false;
						break;
					}
					result = true;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		if (result) {
			try {
				SendEmail.sendTxtMail("346671169@qq.com", "服务器IP修改：" + newIp);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
