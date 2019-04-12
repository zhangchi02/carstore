package com.huawei.carstore.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.carstore.entity.Item;
import com.huawei.carstore.entity.User;

public class DbManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

	private static String DBHOST = "49.4.23.221";
	private static Integer DBPORT = 3306;
	private static final String DBNAME = "ShoppingMallDB";
	private static final String DBUSER = "apm";
	private static final String DBPWD = "paasapm";
//	private static final String PARAMETER = "?useSSL=false&serverTimezone=GMT%2B8";
	private static final String PARAMETER = "?useSSL=false";

	private static Connection conn = null;
	private static PreparedStatement prep = null;
	private static DbManager dbManager = new DbManager();

	private static final String findAllProducts = "SELECT * FROM product_table order by price";
	private static final String insertProduct = " insert into product_table (id,name, price)  values (?, ?, ?)";
	private static final String insertProductWithError = " insert into product_table1 (id,name, price)  values (?, ?, ?)";
	private static final String insertUser = " insert into user_table (id,name)  values (?, ?)";
	private static final String insertUserWithEror = " insert into user_table1 (id,name)  values (?, ?)";
	private static final String select = "SELECT * FROM product_table WHERE id=?";
	private static final String findProducts = "SELECT * FROM product_table WHERE name like ? order by price";
	private static final String findUsers = "SELECT * FROM user_table WHERE name=? and password=?";
	private static final String selectWithError = "SELECT * FROM product_table1 WHERE id=?";
	private static final String insertPayment = "INSERT INTO `payment_table` (`userid`, `productid`) VALUES (?, ?)";
	private static final String insertPaymentWrong = "INSERT INTO `payment_table` (`userid`, `productid`) VALUES [?, ?]";
	boolean isPersistentceError = false;

//	private String JDBCNAME = "com.mysql.cj.jdbc.Driver";
	private String JDBCNAME = "com.mysql.jdbc.Driver";

	public static int errorNum = 0;

	private DbManager() {
		LOGGER.info("DbManager created");
		getConnection();
	}
	
	public static synchronized DbManager getDbManager(){
		if(dbManager==null){
			dbManager = new DbManager();
		}
		return dbManager;
	}

	private Connection getConnection(){
		if(null != System.getenv("MYSQL_DB_IP")){
			DBHOST = System.getenv("MYSQL_DB_IP");
		}else if(null != System.getProperty("MYSQL_DB_IP")){
			DBHOST = System.getProperty("MYSQL_DB_IP");
		}
		
		if(null != System.getenv("MYSQL_DB_PORT")){
			DBPORT = Integer.parseInt(System.getenv("MYSQL_DB_PORT"));
		}else if(null != System.getProperty("MYSQL_DB_PORT")){
			DBPORT = Integer.parseInt(System.getProperty("MYSQL_DB_PORT"));
		}
		String url = "jdbc:mysql://" + DBHOST + ":" + DBPORT + "/" + DBNAME + PARAMETER;
		try {
			Class.forName(JDBCNAME);
			LOGGER.info("DB driver load success.");
			LOGGER.info("Get DB connection start.");
			conn = DriverManager.getConnection(url, DBUSER, DBPWD);
			LOGGER.info("Get DB connection success.");
		} catch (SQLException e) {
			LOGGER.error("Get DB connection error: {}",e);
		} catch (ClassNotFoundException e) {
			LOGGER.error("DB driver load error:", e);
		}
		return conn;
	}
	public static PreparedStatement getPreparedStatement(Connection connection,String sql) throws SQLException {
		prep = connection.prepareStatement(sql);
		return prep;
	}

	public static void close(PreparedStatement prep) throws SQLException {
		if (prep != null) {
			prep.close();
		}
	}

	public List<Item> findAllProducts() {
		List<Item> toReturn = new ArrayList<Item>();
		try {
			prep = DbManager.getPreparedStatement(conn,findAllProducts);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				Double password = rs.getDouble("price");
				toReturn.add(new Item(id, name, password));
			}
			LOGGER.info("find products from db!");
		} catch (Exception e) {
			LOGGER.error("Got an exception! ");
			LOGGER.error(e.getMessage());
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

	public boolean addItem(Item item) {
		try {
			prep = (isPersistentceError == true) ? DbManager.getPreparedStatement(conn,insertProductWithError)
					: DbManager.getPreparedStatement(conn,insertProduct);
			prep.setLong(1, item.getId());
			prep.setString(2, item.getName());
			prep.setDouble(3, item.getPrice());
			prep.execute();
			return true;
		} catch (Exception e) {
			LOGGER.error("Got an exception! --- addItem");
			LOGGER.error(e.getMessage());
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public Item searchItem(long id) {
		Item toReturn = null;
		try {
			prep = (isPersistentceError == true) ? DbManager.getPreparedStatement(conn,selectWithError)
					: DbManager.getPreparedStatement(conn,select);
			prep.setLong(1, id);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				Long longId = rs.getLong("id");
				String name = rs.getString("name");
				Double price = rs.getDouble("price");
				toReturn = new Item(longId, name, price);
			}
		} catch (Exception e) {
			LOGGER.error("Got an exception! {}", e);
			LOGGER.error(e.getMessage());
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

	public boolean addUser(User newUser) {
		try {
			prep = (isPersistentceError == true) ? DbManager.getPreparedStatement(conn,insertUserWithEror)
					: DbManager.getPreparedStatement(conn,insertUser);
			prep.setString(1, newUser.getName());
			prep.setString(2, newUser.getPassword());
			prep.execute();
			return true;
		} catch (Exception e) {
			LOGGER.error("Got an exception! --- addItem");
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			;
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void setPersistenceError(Boolean isSet) {
		isPersistentceError = isSet;
	}

	public List<Item> findProductsByName(String productName) {
		List<Item> toReturn = new LinkedList<Item>();
		try {
			prep = DbManager.getPreparedStatement(conn,findProducts);

			prep.setString(1, productName);
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				Long longId = rs.getLong("id");
				Double price = rs.getDouble("price");
				System.out.println(longId);
				toReturn.add(new Item(longId, name, price));
			}
		} catch (Exception e) {
			LOGGER.error("Got an exception! ");
			LOGGER.error(e.getMessage());
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

	public List<User> findUsers(User user) {
		List<User> toReturn = new LinkedList<User>();
		try {
			prep = DbManager.getPreparedStatement(conn,findUsers);
			prep.setString(1, user.getName());
			prep.setString(2, user.getPassword());
			ResultSet rs = prep.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				toReturn.add(new User(id, name, password));
			}
		} catch (SQLException e) {
			LOGGER.error("Got an exception! ");
			LOGGER.error(e.getMessage());
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

	public List<Item> findProductsById(long id) throws SQLException {
		List<Item> toReturn = new LinkedList<Item>();
		try {
			prep = DbManager.getPreparedStatement(conn,findProducts);
			prep.setLong(1, id);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				Long longId = rs.getLong("id");
				Double price = rs.getDouble("price");

				toReturn.add(new Item(longId, name, price));
			}
		} catch (Exception e) {
			LOGGER.error("Got an exception! ");
			LOGGER.error(e.getMessage());
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}

	public boolean addPayment(long userId, long productId) {
		String sqlstr = "";
		LOGGER.info("num is {}", errorNum);
		if (errorNum < 8) {
			sqlstr = insertPayment;
			errorNum++;
		} else if (errorNum < 10) {
			sqlstr = insertPaymentWrong;
			errorNum++;
		} else {
			errorNum = 0;
		}
		try {
			prep = DbManager.getPreparedStatement(conn,sqlstr);
			prep.setLong(1, userId);
			prep.setLong(2, productId);
			prep.execute();
			return true;
		} catch (Exception e) {
			LOGGER.error("Got an exception! --- add payment");
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				DbManager.close(prep);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
