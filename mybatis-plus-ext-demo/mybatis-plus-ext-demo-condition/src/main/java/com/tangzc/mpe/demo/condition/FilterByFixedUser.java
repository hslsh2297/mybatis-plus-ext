package com.tangzc.mpe.demo.condition;

import com.tangzc.mpe.condition.metadata.IDynamicConditionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Component
public class FilterByFixedUser implements IDynamicConditionHandler {

    @Autowired
    private HttpServletRequest request;

    @Override
    public List<Object> values() {

        return Collections.singletonList("111");
    }
}
