package com.github.nekitos911.msuserservice.enums;

import com.github.nekitos911.msuserservice.validator.markers.BankGroup;
import com.github.nekitos911.msuserservice.validator.markers.GosUslugiGroup;
import com.github.nekitos911.msuserservice.validator.markers.MailGroup;
import com.github.nekitos911.msuserservice.validator.markers.MobileGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Source {
    MAIL(TypeConstants.MAIL, new Class[] {MailGroup.class}),
    MOBILE(TypeConstants.MOBILE, new Class[] {MobileGroup.class}),
    BANK(TypeConstants.BANK, new Class[] {BankGroup.class}),
    GOSUSLUGI(TypeConstants.GOSUSLUGI, new Class[] {GosUslugiGroup.class});

    private final String code;
    private final Class<?>[] validators;

    public interface TypeConstants {

        String MAIL = "mail";
        String MOBILE = "mobile";
        String BANK = "bank";
        String GOSUSLUGI = "gosuslugi";
    }
}
