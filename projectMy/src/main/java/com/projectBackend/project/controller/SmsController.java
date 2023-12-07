package com.projectBackend.project.controller;

import com.projectBackend.project.service.SmsService;
import lombok.*;
import net.nurigo.sdk.NurigoApp;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Setter
@Getter
@ToString
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;
    private final DefaultMessageService messageService;
    private String api = "NCSX0MYODTVI4R8T";
    private String secretApi = "U35KK751OXT6FH57ZHJZLNAQITFEMUUX";
    messageService = NurigoApp.INSTANCE.initialize(api, secretApi, "https://api.coolsms.co.kr");




    @GetMapping("/send-mms")
    public SingleMessageSentResponse sendMmsByResourcePath(@RequestParam String tel) throws IOException {
        String num = smsService.generateAuthCode();
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01091881544");
        message.setTo(tel);
        message.setText("인증번호 입니다. " + num);

        // 여러 건 메시지 발송일 경우 send many 예제와 동일하게 구성하여 발송할 수 있습니다.
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}
