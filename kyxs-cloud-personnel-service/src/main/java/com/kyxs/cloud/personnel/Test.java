//package com.kyxs.cloud.personnel;
//
//public class Test {
//    public static void main(String[] args) {
//        String content = "中共党员,中共预备党员,共青团员,民革会员,民盟盟员,民建会员,民进会员,农工党党员,致公党党员,九三学社社员,台盟盟员,无党派民主人士,群众,其他";
//        String[] arr = content.split(",");
//        int i = 1;
//        for(String s:arr){
//            System.out.println("INSERT INTO `code_item` (`id`, `cus_id`, `tenant_id`, `set_id`, `code_name`, `code_status`, `code_sort`, `create_time`, `update_time`, `creator`, `operator`) \n" +
//                    "VALUES ('"+(1600+i)+"', 1, 1, '16', '"+s+"', '1', "+i+", NULL, NULL, NULL, NULL);");
//            i++;
//        }
//    }
//}
