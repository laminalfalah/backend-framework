package io.github.laminalfalah.backend.mvc.controller;

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 *
 * io.github.laminalfalah.backend.mvc.controller
 *
 * This is part of the backend-framework.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.github.laminalfalah.backend.common.helper.ResponseHelper;
import io.github.laminalfalah.backend.common.payload.response.ResponseError;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author laminalfalah on 16/10/21
 */

@Slf4j
@Controller
@RequestMapping(value = {"${server.error.path:${error.path:/error}}"})
public class BasicErrorController extends AbstractErrorController {

    @Autowired
    public BasicErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @GetMapping
    public ResponseEntity<ResponseError> error(HttpServletRequest request) {
        HttpStatus status = super.getStatus(request);
        return status == HttpStatus.NO_CONTENT
            ? ResponseEntity.noContent().build()
            : ResponseEntity.status(status).body(ResponseHelper.set(status, status.getReasonPhrase()));
    }

}
