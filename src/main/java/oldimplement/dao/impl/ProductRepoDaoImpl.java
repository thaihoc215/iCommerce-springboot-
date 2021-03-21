package oldimplement.dao.impl;

import oldimplement.dao.ProductRepoDao;
import oldimplement.dto.request.UpdatePriceRequest;
import oldimplement.entity.Product;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//@Component
public class ProductRepoDaoImpl implements ProductRepoDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> filterProducts(String brand, String color) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> productRoot = cq.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(brand)) {
//            predicates.add(cb.equal(productRoot.get("brand"), brand));
        }
        if (StringUtils.isNotBlank(color)) {
//            predicates.add(cb.equal(productRoot.get("color"), color));
        }
        if (predicates.size() > 0) {
            cq.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<Product> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Product> searchProducts(String query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> productRoot = cq.from(Product.class);
        if (StringUtils.isNotBlank(query)) {
            cq.where(cb.like(productRoot.get("name"), "%" + query + "%"));
        }

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Product> sortProducts(boolean nameAsc, boolean priceAsc) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> productRoot = cq.from(Product.class);
        cq.orderBy(nameAsc ? cb.asc(productRoot.get("name")) : cb.desc(productRoot.get("name")),
                priceAsc ? cb.asc(productRoot.get("price")) : cb.desc(productRoot.get("price")));

        TypedQuery<Product> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public int updateProductPrice(UpdatePriceRequest updatePriceRequest, Timestamp updateTime) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Product> cq = cb.createCriteriaUpdate(Product.class);

        Root<Product> productRoot = cq.from(Product.class);
        cq.set(productRoot.get("price"), updatePriceRequest.getNewPrice());
        cq.set(productRoot.get("modifiedTime"), updateTime);
//        cq.set(productRoot.get("modified_by"), new Timestamp(System.currentTimeMillis()));
        cq.where(cb.equal(productRoot.get("id"), updatePriceRequest.getProductId()));

        return em.createQuery(cq).executeUpdate();
    }

}
