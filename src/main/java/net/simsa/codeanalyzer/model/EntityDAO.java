package net.simsa.codeanalyzer.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

// TODO: Move to hibernate. This is a silly aberration.
public class EntityDAO {

    static EntityDAO self;

    Connection conn = null;
    PreparedStatement classPstmt = null;

    public EntityDAO() {
	setup();
    }

    public static EntityDAO getManager() {
	if (self == null) {
	    self = new EntityDAO();
	}
	return self;
    }

    public void setup() {
	DataSource dataSource = DataSourceFactory.getDataSource();
	try {
	    conn = dataSource.getConnection();
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}

	try {
	    classPstmt = conn
		    .prepareStatement("insert into JClass (fullyQualifiedName, simpleName, sourcePath, sourceFile, "
			    + "packageName, organization, superclassName) values (?,?,?,?,?,?,?);");
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    public void teardown() {
	try {
	    classPstmt.close();
	} catch (SQLException se) {
	}
	try {
	    conn.close();
	} catch (SQLException se) {
	}
    }

    public void insert(JClass jclass) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {
	    DataSource dataSource = DataSourceFactory.getDataSource();
	    conn = dataSource.getConnection();

	    String sql = "insert into JClass (fullyQualifiedName, simpleName, sourcePath, sourceFile, "
		    + "packageName, organization, superclassName) values (?,?,?,?,?,?,?);";
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, jclass.getFullyQualifiedName());
	    pstmt.setString(2, jclass.getSimpleName());
	    pstmt.setString(3, jclass.getSourcePath());
	    pstmt.setString(4, jclass.getSourceFile());
	    pstmt.setString(5, jclass.getPackageName());
	    pstmt.setString(6, jclass.getOrganization());
	    pstmt.setString(7, jclass.getSuperclassName());
	    pstmt.execute();
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	} finally {
	    try {
		if (conn != null) {
		    conn.close();
		}
	    } catch (SQLException se) {
	    }
	}
    }

}
