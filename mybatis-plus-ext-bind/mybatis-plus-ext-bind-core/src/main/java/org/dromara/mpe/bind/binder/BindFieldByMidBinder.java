package org.dromara.mpe.bind.binder;

import org.dromara.mpe.bind.builder.ByMidResultBuilder;
import org.dromara.mpe.bind.metadata.BindFieldByMidDescription;
import org.dromara.mpe.bind.metadata.FieldDescription;
import org.dromara.mpe.bind.metadata.MidConditionDescription;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 执行字段绑定的绑定器
 *
 * @author don
 */
@Slf4j
@NoArgsConstructor(staticName = "newInstance")
public class BindFieldByMidBinder<BEAN> implements IBinder<BEAN, BindFieldByMidDescription, MidConditionDescription> {

    @Override
    public <ENTITY> void fillData(List<BEAN> beans, FieldDescription.ConditionSign<ENTITY, MidConditionDescription> conditionSign,
                                  List<BindFieldByMidDescription> fieldAnnotations) {

        ByMidResultBuilder.FillDataCallback fillDataCallback = new ByMidResultBuilder.FillDataCallback() {
            @Override
            public String[] selectColumns(List<?> beans, FieldDescription.ConditionSign<?, MidConditionDescription> conditionSign,
                                          List<? extends FieldDescription<?, MidConditionDescription>> fieldDescriptions) {

                List<String> columns = fieldAnnotations.stream()
                        .map(BindFieldByMidDescription::getRealColumnName)
                        .collect(Collectors.toList());

                // 追加条件查询字段，用于标识查询数据的
                for (MidConditionDescription condition : conditionSign.getConditions()) {
                    columns.add(condition.getJoinColumnName());
                }

                return columns.toArray(new String[0]);
            }

            @Override
            public List<?> changeDataList(Object bean, FieldDescription<?, MidConditionDescription> fieldAnnotation, List<?> entities) {

                // 将对象集合转化为单字段的集合
                return entities.stream().map(entity -> {
                    try {
                        return ((BindFieldByMidDescription) fieldAnnotation).getBindFieldGetMethod().invoke(entity);
                    } catch (Exception e) {
                        log.error("绑定属性获取值失败", e);
                        return null;
                    }
                }).collect(Collectors.toList());
            }
        };
        ByMidResultBuilder.newInstance(beans, conditionSign, fieldAnnotations, fillDataCallback).fillData();
    }
}
