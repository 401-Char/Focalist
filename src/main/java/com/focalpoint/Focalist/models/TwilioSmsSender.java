package com.focalpoint.Focalist.models;

import com.focalpoint.Focalist.config.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    @Autowired
    TwilioConfiguration twilioConfiguration;

//    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
//        this.twilioConfiguration = twilioConfiguration;
//    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
        } else {
            throw new IllegalArgumentException(String.format("The phone number %s is invalid", smsRequest.getPhoneNumber()));
        }
    }

    // TODO: use regex to test for valid phone number
    private boolean isPhoneNumberValid(String phoneNumber) {
        return true;
    }
}