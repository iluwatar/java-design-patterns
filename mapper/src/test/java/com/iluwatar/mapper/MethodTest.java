package com.iluwatar.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.iluwatar.mapper.pricing.LeaseRepository;
import com.iluwatar.mapper.pricing.PricingCategory;
import org.junit.jupiter.api.Test;

/**
 * Testing the method validation.
 */
class MethodTest {//NOPMD

  /**
   * Testing the existing repository.
   */
  @Test
  void queryRepository() {
    assertNotNull(new LeaseRepository().query(), "Repository query success.");
  }

  /**
   * Testing the repository update.
   */
  @Test
  void queryRepositoryExist() {
    final LeaseRepository leaseRepository = new LeaseRepository();
    leaseRepository.query().add(new Pricing("a", 1));
    assertEquals(1, leaseRepository.query().size(), "Repository query content correct.");
  }

  /**
   * Testing the object in the repository.
   */
  @Test
  void findObjectInRepo() {
    final LeaseRepository leaseRepository = new LeaseRepository();
    leaseRepository.query().add(new Pricing("a", 1));
    assertNotNull(leaseRepository.findLease(0), "Find success.");
  }

  /**
   * Testing the object not in the repository.
   */
  @Test
  void findNullInRepo() {
    final LeaseRepository leaseRepository = new LeaseRepository();
    assertNull(leaseRepository.findLease(0), "Cannot find target.");
  }

  /**
   * Testing the category existing.
   */
  @Test
  void queryCategory() {
    assertNotNull(new PricingCategory().query(), "Category query success.");
  }

  /**
   * Testing the category update.
   */
  @Test
  void queryCategoryExist() {
    final PricingCategory pricingCategory = new PricingCategory();
    pricingCategory.query().add(new Pricing("a", 1));
    assertEquals(1, pricingCategory.query().size(), "Category query content correct.");
  }

  /**
   * Testing the object in the category.
   */
  @Test
  void findObjectInCategory() {
    final PricingCategory pricingCategory = new PricingCategory();
    pricingCategory.query().add(new Pricing("a", 1));
    assertNotNull(pricingCategory.findObject("a"), "Successfully find object.");
  }

  /**
   * Testing the object not in the category.
   */
  @Test
  void findNullInCategory() {
    final PricingCategory pricingCategory = new PricingCategory();
    assertNull(pricingCategory.findObject("a"), "Cannot find the object in category.");
  }

  /**
   * Testing the repository search in mapper.
   */
  @Test
  void mapperQueryRepo() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertNotNull(pricingMapper.queryRepository(), "Mapper query the repository.");
  }

  /**
   * Testing the correct update for the repository search in mapper.
   */
  @Test
  void mapperQueryRepoSize() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("f", 1));
    assertEquals(1, pricingMapper.queryRepository().size(), "Mapper query result correct.");
  }

  /**
   * Testing the category search in mapper.
   */
  @Test
  void mapperQueryCate() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertNotNull(pricingMapper.queryCategory(), "Mapper query category.");
  }

  /**
   * Testing the correct update for the category search in mapper.
   */
  @Test
  void mapperQueryCateSize() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("f", 1));
    assertEquals(1, pricingMapper.queryCategory().size(), "Mapper query result correct.");
  }

  /**
   * Testing the lease operation.
   */
  @Test
  void mapperAddLease() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    pricingMapper.addLease(new Pricing("a", 1));
    assertEquals(2, pricingMapper.queryRepository().size(), "Mapper add a lease.");
  }

  /**
   * Testing the lease operation of the correct content.
   */
  @Test
  void mapperAddLeaseExact() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    assertEquals("a", pricingMapper.queryRepository().get(0).getName(), "Mapper add result correct.");
  }

  /**
   * Testing the object being correctly added in the category.
   */
  @Test
  void mapperAddObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertTrue(pricingMapper.addObject(new Pricing("a", 1)), "Mapper add object correct.");
  }

  /**
   * Testing the object in the category cannot be duplicate.
   */
  @Test
  void mapperAddObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("a", 1));
    assertFalse(pricingMapper.addObject(new Pricing("a", 1)),
            "Mapper cannot add duplicate object in category");
  }

  /**
   * Testing the object be removed successfully.
   */
  @Test
  void mapperRemoveObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("a", 1));
    assertTrue(pricingMapper.removeObject("a"), "Mapper remove object successfully.");
  }

  /**
   * Testing the object cannot be removed without existence.
   */
  @Test
  void mapperRemoveObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.removeObject("a"), "Mapper cannot remove object without existence.");
  }

  /**
   * Testing the object can be modified successfully.
   */
  @Test
  void mapperModifyObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.modifyObject("a", new Pricing("a", 1)),
            "Mapper modify object successfully.");
  }

  /**
   * Testing the object cannot be modified without existence.
   */
  @Test
  void mapperModifyObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("a", 1));
    assertTrue(pricingMapper.modifyObject("a", new Pricing("b", 2)),
            "object cannot be modified without existence.");
  }

  /**
   * Testing the lease can be removed successfully.
   */
  @Test
  void mapperRemoveLeaseSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    assertTrue(pricingMapper.removeLease(0), "The lease can be removed successfully.");
  }

  /**
   * Testing the lease cannot be removed without existence.
   */
  @Test
  void mapperRemoveLeaseFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.removeLease(0), "The lease cannot be removed without existence.");
  }

  /**
   * Testing the object can be leased successfully.
   */
  @Test
  void mapperLeaseObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    assertTrue(pricingMapper.leaseObject(0), "The object can be leased successfully.");
  }

  /**
   * Testing the object cannot be leased without existence.
   */
  @Test
  void mapperLeaseObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.leaseObject(0), "The object cannot be leased without existence.");
  }

  /**
   * Testing the object can be returned successfully.
   */
  @Test
  void mapperReturnObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    pricingMapper.leaseObject(0);
    assertTrue(pricingMapper.returnObject(0), "The object can be returned successfully.");
  }

  /**
   * Testing the object cannot be returned without existence.
   */
  @Test
  void mapperReturnObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.returnObject(0), "The object cannot be returned without existence.");
  }

  /**
   * Testing the customer can query the repository correctly.
   */
  @Test
  void customerQueryRepo() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Customer customer = new Customer(pricingMapper);
    assertEquals(pricingMapper.queryRepository(), customer.queryRepository(),
            "The customer can query the repository correctly.");
  }

  /**
   * Testing the customer can get the repository.
   */
  @Test
  void customerQueryRepoSize() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Customer customer = new Customer(pricingMapper);
    assertEquals(0, customer.queryRepository().size(), "The customer can get the repository.");
  }

  /**
   * Testing the customer can get the category correctly.
   */
  @Test
  void customerQueryCate() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Customer customer = new Customer(pricingMapper);
    assertEquals(pricingMapper.queryCategory(), customer.queryCategory(),
            "The customer can get the category correctly.");
  }

  /**
   * Testing the customer can get the category.
   */
  @Test
  void customerQueryCateSize() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Customer customer = new Customer(pricingMapper);
    assertEquals(0, customer.queryCategory().size(), "The customer can get the category.");
  }

  /**
   * Testing the lease class can get the repository correctly.
   */
  @Test
  void leaseQueryRepo() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    assertEquals(pricingMapper.queryRepository(), lease.queryLease(),
            "The lease class can get the repository correctly.");
  }

  /**
   * Testing the lease class can get the repository.
   */
  @Test
  void leaseQueryRepoSize() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    assertEquals(0, lease.queryLease().size(),
            "The lease class can get the repository.");
  }

  /**
   * Testing the asset can get the category correctly.
   */
  @Test
  void assetQueryCate() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    assertEquals(pricingMapper.queryCategory(), asset.queryCategory(),
            "The asset can get the category correctly.");
  }

  /**
   * Testing the asset can get the category.
   */
  @Test
  void assetQueryCateSize() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    assertEquals(0, asset.queryCategory().size(), "The asset can get the category.");
  }

  /**
   * Testing the lease class can add leases.
   */
  @Test
  void leaseAddLease() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    lease.addLease(new Pricing("a", 1));
    assertEquals(2, lease.queryLease().size(), "The lease class can add leases.");
  }

  /**
   * Testing the lease class can add a lease correctly.
   */
  @Test
  void leaseAddLeaseExact() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    assertEquals("a", lease.queryLease().get(0).getName(),
            "The lease class can add a lease correctly.");
  }

  /**
   * Testing the asset can add a object in the category.
   */
  @Test
  void assetAddObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    assertTrue(asset.addObject(new Pricing("a", 1)), "The asset can add a object in the category.");
  }

  /**
   * Testing the asset cannot add the duplicate object.
   */
  @Test
  void assetAddObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    asset.addObject(new Pricing("a", 1));
    assertFalse(asset.addObject(new Pricing("a", 1)), "The asset cannot add the duplicate object.");
  }

  /**
   * Testing the asset can remove the object.
   */
  @Test
  void assetRemoveObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    asset.addObject(new Pricing("a", 1));
    assertTrue(asset.removeObject("a"), "The asset can remove the object.");
  }

  /**
   * Testing the asset cannot remove the object without existence.
   */
  @Test
  void assetRemoveObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    assertFalse(asset.removeObject("a"), "The asset cannot remove the object without existence.");
  }

  /**
   * Testing the asset cannot modify the object without existence.
   */
  @Test
  void assetModifyObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    assertFalse(asset.modifyObject("a", new Pricing("a", 1)),
            "The asset cannot modify the object without existence.");
  }

  /**
   * Testing the asset can modify the object correctly.
   */
  @Test
  void assetModifyObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Asset asset = new Asset(pricingMapper);
    asset.addObject(new Pricing("a", 1));
    assertTrue(asset.modifyObject("a", new Pricing("b", 2)),
            "The asset can modify the object correctly.");
  }

  /**
   * Testing the lease class can remove the lease correctly.
   */
  @Test
  void leaseRemoveLeaseSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    assertTrue(lease.removeLease(0), "The lease class can remove the lease correctly.");
  }

  /**
   * Testing the lease class cannot remove the lease without existence.
   */
  @Test
  void leaseRemoveLeaseFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    assertFalse(lease.removeLease(0), "The lease class cannot remove the lease without existence.");
  }

  /**
   * Testing the lease class can lease an object correctly.
   */
  @Test
  void leaseLeaseObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    assertTrue(lease.leaseObject(0), "The lease class can lease an object correctly.");
  }

  /**
   * Testing the lease class cannot lease the object without existence.
   */
  @Test
  void leaseLeaseObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    assertFalse(lease.leaseObject(0), "The lease class cannot lease the object without existence.");
  }

  /**
   * Testing the lease class can return an object successfully.
   */
  @Test
  void leaseReturnObjectSuccess() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    lease.leaseObject(0);
    assertTrue(lease.returnObject(0), "The lease class can return an object successfully.");
  }

  /**
   * Testing the lease class cannot return the object without existence.
   */
  @Test
  void leaseReturnObjectFailure() {
    final PricingMapper pricingMapper = new PricingMapper();
    final Lease lease = new Lease(pricingMapper);
    assertFalse(lease.returnObject(0), "The lease class cannot return the object without existence.");
  }
}
