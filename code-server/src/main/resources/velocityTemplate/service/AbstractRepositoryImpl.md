
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.codeaone.boot.core.lang.Page;
import com.codeaone.boot.mybatis.model.BaseTotalSearcher;

/**
 * 
 * @author tushiqiao
 * @version $Id: AbstractRepositoryImpl.java, v 0.1 2017年5月20日 下午5:24:46 tushiqiao Exp $
 */
public abstract class AbstractRepositoryImpl {


    /**
     * 多租户Id
     * @return
     */
    protected String getTntInstId() {
        //String tntInstId = TNTInstIDEnum.SMARTPAY.getCode();
        String tntInstId = "SMARTDEF";
        /*try {
            EventContext context = EventContextUtil.getEventContextFromTracer();
            tntInstId = context.getTntInstId();
            if (StringUtils.isBlank(tntInstId)) {
                tntInstId = TNTInstIDEnum.SMARTPAY.getCode();
                LoggerUtil.error(LOGGER, "租户信息从上下文中获取为空，设置默认值：${0}", tntInstId);
            }
        } catch (Exception e) {
            LoggerUtil.error(e, LOGGER, "租户信息从上下文中获取失败，设置默认值：${0}", tntInstId);
        }*/
        return tntInstId;
    }
    
    protected <T> T copyProperties(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        @SuppressWarnings("deprecation")
        T obj = BeanUtils.instantiate(target);
        BeanUtils.copyProperties(source, obj);
        return obj;
    }

    @SuppressWarnings("deprecation")
    protected <T> List<T> copyPropertiesList(List<? extends Object> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (source != null) {
            for (Object object : source) {
                T obj = BeanUtils.instantiate(target);
                BeanUtils.copyProperties(object, obj);
                list.add(obj);
            }
        }
        return list;
    }
    
    protected <T> Page<T> copyToPage(List<? extends Object> source, Class<T> target,
                                     BaseTotalSearcher context) {
        List<T> projectList = copyPropertiesList(source, target);
        Page<T> page = new Page<T>();
        page.setPageNo(context.getPageNo());
        page.setPageSize(context.getPageSize());
        page.setTotalCount(context.getTotalCount());
        page.setResult(projectList);
        return page;
    }
    
    /*该方法用于传入某实例对象以及对象方法名、修改值，通过放射调用该对象的某个set方法设置修改值*/
    protected void setProperty(Object beanObj, String property, Object value) {
        //此处应该判断beanObj,property不为null
        try {
            PropertyDescriptor pd = new PropertyDescriptor(property, beanObj.getClass());
            Method setMethod = pd.getWriteMethod();
            if (setMethod == null) {

            }
            setMethod.invoke(beanObj, value);
        } catch (Exception e) {
        }
    }

}