package zhou.wu.boot.log4j.demo;

import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI服务
 * @author zhou.wu
 * @date 2021/12/15
 **/
public class RmiServer {

    public static void main(String[] args) throws RemoteException, NamingException, AlreadyBoundException {
        Registry registry = LocateRegistry.createRegistry(8080);
        registry.bind("look", new ReferenceWrapper(
                new Reference(null, "zhou.wu.boot.log4j.demo.RmiLook",null)
        ));
        System.out.println("RMI服务已启动");
    }

}
