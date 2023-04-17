package test;

import com.itheima.po.Customer;
import com.itheima.po.MyBatisUtils;
import com.itheima.po.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;
public class MyBatisTest {
    //普通查找
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
        customer.setPhone("1999955555");
        customer.setId(6);//需要修改的id
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
        customer.setId(6);//需要删除的id
        int rows = sqlSession.delete("com.itheima.mapper.CustomerMapper.del",customer);
        if(rows>0){
            System.out.println("成功删除"+rows+"行");

        }else {
            System.out.println("删除失败");
        }
        sqlSession.commit();
        sqlSession.close();
    }
    //utils插入
    @Test
    public void addCustomer(){
        SqlSession sqlSession = MyBatisUtils.getSession();
        Customer customer = new Customer();
        customer.setUsername("rose");
        customer.setJobs("student");
        customer.setPhone("1999955555");
        int rows = sqlSession.insert("com.itheima.mapper."+"CustomerMapper.insertCustomer",customer);
        System.out.println(customer.getId());
        if(rows>0){
            System.out.println("成功插入了"+rows+"行");
        }else{
            System.out.println("操作失败");
        }
        sqlSession.commit();
        sqlSession.close();

    }
    //utils更新
    @Test
    public void updateCustomer() throws Exception {
        // 获取 SqlSession
        SqlSession sqlSession = MyBatisUtils.getSession();
        Customer customer = new Customer();
        customer.setId(4);
        customer.setUsername("rose");
        customer.setJobs("programmer");
        customer.setPhone("13311111111");
        // 执行 sqlSession 的更新方法，返回的是 SQL 语句影响的行数
        int rows = sqlSession.update("com.itheima.mapper" + ".CustomerMapper.updateCustomer", customer);
        //通过返回结果判断更新操作是否执行成功
        if (rows > 0) {
            System.out.println("您成功修改了" + rows + "条数据！");
        } else {
            System.out.println("修改失败");
        }
        sqlSession.commit();
        sqlSession.close();
    }
    //utils删除
    @Test
    public void deleteCustomer() throws Exception {
        // 获取 SqlSession
        SqlSession sqlSession = MyBatisUtils.getSession();
        // 执行 sqlSession 的更新方法，返回的是 SQL 语句影响的行数
        int rows = sqlSession.delete("com.itheima.mapper" + ".CustomerMapper.deleteCustomer", 6);
        //通过返回结果判断更新操作是否执行成功
        if (rows > 0) {
            System.out.println("您成功删除了" + rows + "条数据！");
        } else {
            System.out.println("删除失败");
        }
        sqlSession.commit();
        sqlSession.close();
    }
    //使用resultMap映射结果
    @Test
    public void findall(){
        SqlSession sqlSession = MyBatisUtils.getSession();
        List<User> list = sqlSession.selectList("UserMapper.findAll");
        for(User user:list){
            System.out.println(user);
        }
        sqlSession.close();
    }
}
