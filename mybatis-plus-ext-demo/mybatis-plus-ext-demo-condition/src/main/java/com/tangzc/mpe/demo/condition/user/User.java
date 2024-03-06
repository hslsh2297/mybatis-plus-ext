package com.tangzc.mpe.demo.condition.user;

import com.tangzc.autotable.annotation.ColumnComment;
import com.tangzc.mpe.annotation.InsertFillTime;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.base.entity.BaseLogicEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Table(comment = "用户")
public class User extends BaseLogicEntity<String, LocalDateTime> {

    @ColumnComment("主键")
    private String id;
    @Column(comment = "姓名", length = 300)
    private String name;
    @InsertFillTime
    @Column(value = "registered_date1", comment = "注册时间")
    private Long registeredDate;
}
