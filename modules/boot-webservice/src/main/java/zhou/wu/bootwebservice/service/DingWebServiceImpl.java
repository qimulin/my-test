package zhou.wu.bootwebservice.service;

import javax.jws.WebService;

/**
 * @author Lin.xc
 * @date 2020/4/1
 */
@WebService(
        targetNamespace = WsConst.NAMESPACE_URI, //wsdl命名空间
        serviceName = "DingWebService",           //服务name名称
        endpointInterface = "zhou.wu.bootwebservice.service.DingWebService")//指定发布webservcie的接口类，此类也需要接入@WebService注解
public class DingWebServiceImpl implements DingWebService {

    @Override
    public String GetSchema(String GetSchema) {
        System.out.println("GetSchema");
        return "";
    }

    @Override
    public String GetSchemaList() {
        System.out.println("GetSchemaList");
        return "GetSchemaList";
    }

    @Override
    public String GetList(String userCode, String schemaCode, String filter) {
        System.out.println("GetList");
        return "GetList";
    }

    @Override
    public String Invoke(String userCode, String schemaCode, String methodName, String param) {
        System.out.println("Invoke");
        return "Invoke";
    }
}
