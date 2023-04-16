package test;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;


import com.itheima.po.Customer;
public class MyBatisTest {
    @Test
    public void findCustomerByTest()throws Exception{
        //1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //2、根据配置文件构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3、通过sqlSessionFactory创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4、sqlSession执行映射文件中定义的sql
        Customer customer = sqlSession.selectOne("com.itheima.mapper"+".CustomerMapper.find",2);// O 为查询id的值
        //Customer customer = sqlSession.selectOne("Cust.find",1);
        System.out.println(customer.toString());
        sqlSession.close();
    }
    //模糊查找
    @Test
    public void findCustomerByName()throws Exception {
        //1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //2、根据配置文件构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3、通过sqlSessionFactory创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //4、sqlSession执行映射文件中定义的sql
        //Customer customer = sqlSession.selectOne("com.itheima.mapper" + ".CustomerMapper.findname", "2");
        //Customer customer = sqlSession.selectOne("Cust.find",1);
        List<Customer> customers = sqlSession.selectList("com.itheima.mapper" + ".CustomerMapper.findname", "j");
        for(Customer customer:customers){
            System.out.println(customer);
        }
        sqlSession.close();
    }
    //插入数据
    @Test
    public void addCustomerTest()throws Exception {
        //1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //2、根据配置文件构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3、通过sqlSessionFactory创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Customer customer = new Customer();
        customer.setUsername("rose");
        customer.setJobs("student");
        customer.setPhone("18600299868");
        int rows = sqlSession.insert("com.itheima.mapper.CustomerMapper.add",customer);
        if(rows>0){
            System.out.println("成功插入"+rows);

        }else {
            System.out.println("插入失败");
        }
        sqlSession.commit();
        sqlSession.close();
    }
    //修改
    @Test
    public void update()throws Exception {
        //1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //2、根据配置文件构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3、通过sqlSessionFactory创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Customer customer = new Customer();
        customer.setUsername("rose");
        customer.setJobs("student");
        customer.setPhone("19999999999");
        customer.setId(5);//需要修改的id
        int rows = sqlSession.update("com.itheima.mapper.CustomerMapper.update",customer);
        if(rows>0){
            System.out.println("成功更新"+rows+"行");

        }else {
            System.out.println("更新失败");
        }
        sqlSession.commit();
        sqlSession.close();
    }
    //删除
    @Test
    public void delete()throws Exception {
        //1、读取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //2、根据配置文件构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //3、通过sqlSessionFactory创建sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        Customer customer = new Customer();
        customer.setId(5);//需要删除的id
        int rows = sqlSession.delete("com.itheima.mapper.CustomerMapper.del",customer);
        if(rows>0){
            System.out.println("成功删除"+rows+"行");

        }else {
            System.out.println("删除失败");
        }
        sqlSession.commit();
        sqlSession.close();
    }
}
