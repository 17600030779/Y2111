package cn.easybuy.dao.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.param.ProductCategoryParam;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.MybatisUtil;

public class ProductCategoryDaoImpl extends BaseDaoImpl implements ProductCategoryDao {

	public ProductCategoryDaoImpl(Connection connection) {
		super(connection);
	}
	

	public ProductCategoryDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public ProductCategory tableToClass(ResultSet rs) throws Exception {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setId(rs.getInt("id"));
		productCategory.setName(rs.getString("name"));
		productCategory.setParentId(rs.getInt("parentId"));
		productCategory.setType(rs.getInt("type"));
		productCategory.setIconClass(rs.getString("iconClass"));
		return productCategory;
	}
	
	public ProductCategory mapToClass(Map map) throws Exception {
		ProductCategory productCategory = new ProductCategory();
		Object idObject=map.get("id");
		Object nameObject=map.get("name");
		Object parentIdObject=map.get("parentId");
		Object typeObject=map.get("type");
		Object iconClassObject=map.get("iconClass");
		Object parentNameObject=map.get("parentName");
		productCategory.setId(EmptyUtils.isEmpty(idObject)?null:(Integer)idObject);
		productCategory.setName(EmptyUtils.isEmpty(nameObject)?null:(String)nameObject);
		productCategory.setParentId(EmptyUtils.isEmpty(parentIdObject)?null:(Integer)parentIdObject);
		productCategory.setType(EmptyUtils.isEmpty(typeObject)?null:(Integer)typeObject);
		productCategory.setIconClass(EmptyUtils.isEmpty(iconClassObject)?null:(String)iconClassObject);
		productCategory.setParentName(EmptyUtils.isEmpty(parentNameObject)?null:(String)parentNameObject);
		return productCategory;
	}
	
	public List<ProductCategory> queryProductCategorylist(ProductCategoryParam params){
		List<ProductCategory> list=new ArrayList<ProductCategory>();
			SqlSession session=null;
			try{
				session=MybatisUtil.openSqlSession();
				list=session.getMapper(ProductCategoryDao.class).queryProductCategorylist(params);		
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				MybatisUtil.closeSqlSession(session);
			}	
			return list;
		}


	@Override
	public void deleteById(Integer id){
		String sql = " delete from easybuy_product_category where id = ? ";
		Object params[] = new Object[] { id };
		this.executeUpdate(sql.toString(), params);	
	}

	public Integer queryProductCategoryCount(ProductCategoryParam params){
		List<Object> paramsList=new ArrayList<Object>();   
		Integer count=0;
		StringBuffer sql=new StringBuffer("SELECT count(*) count FROM easybuy_product_category where 1=1 ");
		if(EmptyUtils.isNotEmpty(params.getName())){
			sql.append(" and name like ? ");
			paramsList.add("%"+params.getName()+"%");
		}
		if(EmptyUtils.isNotEmpty(params.getParentId())){
			sql.append(" and parentId = ? ");
			paramsList.add(params.getParentId());
		}
		ResultSet resultSet=this.executeQuery(sql.toString(), paramsList.toArray());
		try {
			while (resultSet.next()) {
				count=resultSet.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return count;
	}
	
	public ProductCategory queryProductCategoryById(Integer id){
		List<Object> paramsList=new ArrayList<Object>();   
		ProductCategory productCategory=null;
		StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where id = ? ");
		ResultSet resultSet=this.executeQuery(sql.toString(),new Object[]{id});
		try {
			while (resultSet.next()) {
				productCategory = this.tableToClass(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return productCategory;
	}
	
	public Integer add(ProductCategory productCategory)  {//新增用户信息
    	Integer id=0;
    	try {
    		String sql=" INSERT into easybuy_product_category(name,parentId,type,iconClass) values(?,?,?,?) ";
            Object param[]=new Object[]{productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass()};
            id=this.executeInsert(sql,param);
            productCategory.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	this.closeResource();
        }
    	return id;
    }

	@Override
	public void update(ProductCategory productCategory) {
		try {
        	Object[] params = new Object[] {productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass(),productCategory.getId()};
        	String sql = " UPDATE easybuy_product_category SET name=?,parentId=?,type=?,iconClass=? WHERE id =?  ";
    		this.executeUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	this.closeResource();
        }		
	}
	
	
}
