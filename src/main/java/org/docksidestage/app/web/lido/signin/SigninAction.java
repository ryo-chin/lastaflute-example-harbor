/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.app.web.lido.signin;

import javax.annotation.Resource;

import org.docksidestage.app.web.base.HarborBaseAction;
import org.docksidestage.app.web.base.login.HarborLoginAssist;
import org.docksidestage.mylasta.action.HarborMessages;
import org.lastaflute.web.Execute;
import org.lastaflute.web.response.JsonResponse;

/**
 * @author jflute
 */
public class SigninAction extends HarborBaseAction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Resource
    private HarborLoginAssist harborLoginAssist;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======

    @Execute
    public JsonResponse<Object> index(SigninForm form) {
        validate(form, messages -> moreValidate(form, messages), () -> {
            form.clearSecurityInfo();
            return JsonResponse.asEmptyBody().httpStatus(400);
        });
        harborLoginAssist.login(form.email, form.password, op -> op.rememberMe(form.rememberMe));
        return JsonResponse.asEmptyBody();
    }

    private void moreValidate(SigninForm form, HarborMessages messages) {
        if (isNotEmpty(form.email) && isNotEmpty(form.password)) {
            if (!harborLoginAssist.checkUserLoginable(form.email, form.password)) {
                messages.addErrorsLoginFailure("email");
            }
        }
    }

}