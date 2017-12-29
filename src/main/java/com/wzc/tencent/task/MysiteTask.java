package com.wzc.tencent.task;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wzc.ssm.domain.impl.TencentDomainService;
import com.wzc.ssm.domain.impl.TencentDomainService.Record;
import com.wzc.ssm.dto.impl.EmailOption;
import com.wzc.ssm.email.impl.EmailService;
import com.wzc.ssm.util.IpHelper;

@Component("myTask")
public class MysiteTask {

	@Autowired
	private TencentDomainService tencentDomainService;
	@Autowired
	private EmailService emailService;

	//@Scheduled(cron = "0 0 0/1 * * ?")
	@Scheduled(cron = "0/10 * * * * ?")
	public void ChangeIpTask() {
		try {
			String newIp = IpHelper.getPublicIp();
			Boolean result = false;
			if (newIp == null || newIp.equals("")) {
				return;
			}
			
			List<Record> records = tencentDomainService.getDomainRecords();
			if (records.size()==0) {
				Record record = new Record();
				record.setSubDomain("@");
				record.setRecordType("A");
				record.setValue(newIp);
				record.setRecordLine("默认");
				records.add(record);
				result = true;
			}else {
				for(Record record : records) {
					if(newIp.equals(record.getValue())) {
						break;
					}
					record.setValue(newIp);
					result = true;
				}
			}
			tencentDomainService.setDomainRecord(records);
			if (result) {
				EmailOption option = new EmailOption("346671169@qq.com","默认标题","服务器IP修改为：" + newIp);
				emailService.sendEmail(option);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
