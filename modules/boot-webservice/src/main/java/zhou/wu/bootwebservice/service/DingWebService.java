package zhou.wu.bootwebservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Lin.xc
 * @date 2020/4/1
 */
@WebService
public interface DingWebService {

    @WebMethod(action =WsConst.NAMESPACE_URI+"GetSchema")
    String GetSchema(@WebParam(name = "schemaCode",targetNamespace = WsConst.NAMESPACE_URI)String schemaCode);

    @WebMethod(action =WsConst.NAMESPACE_URI+"GetSchemaList")
    String GetSchemaList();

    @WebMethod(action =WsConst.NAMESPACE_URI+"GetList")
    String GetList(@WebParam(name = "userCode",targetNamespace = WsConst.NAMESPACE_URI) String userCode,
                   @WebParam(name = "schemaCode",targetNamespace = WsConst.NAMESPACE_URI) String schemaCode,
                   @WebParam(name = "filter",targetNamespace = WsConst.NAMESPACE_URI) String filter);

    @WebMethod(action =WsConst.NAMESPACE_URI+"Invoke")
    String Invoke(
            @WebParam(name = "userCode",targetNamespace = WsConst.NAMESPACE_URI) String userCode,
            @WebParam(name = "schemaCode",targetNamespace = WsConst.NAMESPACE_URI) String schemaCode,
            @WebParam(name = "methodName",targetNamespace = WsConst.NAMESPACE_URI) String methodName,
            @WebParam(name = "param",targetNamespace = WsConst.NAMESPACE_URI) String param);

}
