package com.kyxs.cloud.personnel.api.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "客户信息")
@TableName("CUSTOM_INFO")
public class DemoBO {
    @Schema(title = "主键")
    @TableId(value = "id")
    private String id;

    @Schema(title = "名称")
    @TableField(value = "cus_name")
    private String cusName;
}
