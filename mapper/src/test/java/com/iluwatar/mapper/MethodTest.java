/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.iluwatar.mapper.pricing.LeaseRepository;
import com.iluwatar.mapper.pricing.PricingCategory;
import org.junit.jupiter.api.Test;

class MethodTest {

  /**
   * Testing the existing repository.
   */
  @Test
  void queryRepository() {
    assertNotNull(new LeaseRepository().query());
  }

  /**
   * Testing the repository update.
   */
  @Test
  void queryRepositoryExist() {
    LeaseRepository leaseRepository = new LeaseRepository();
    leaseRepository.query().add(new Pricing("a", 1));
    assertEquals(1, leaseRepository.query().size());
  }

  /**
   * Testing the object in the repository.
   */
  @Test
  void findObjectInRepo() {
    LeaseRepository leaseRepository = new LeaseRepository();
    leaseRepository.query().add(new Pricing("a", 1));
    assertNotNull(leaseRepository.findLease(0));
  }

  /**
   * Testing the object not in the repository.
   */
  @Test
  void findNullInRepo() {
    LeaseRepository leaseRepository = new LeaseRepository();
    assertNull(leaseRepository.findLease(0));
  }

  /**
   * Testing the category existing.
   */
  @Test
  void queryCategory() {
    assertNotNull(new PricingCategory().query());
  }

  /**
   * Testing the category update.
   */
  @Test
  void queryCategoryExist() {
    PricingCategory pricingCategory = new PricingCategory();
    pricingCategory.query().add(new Pricing("a", 1));
    assertEquals(1, pricingCategory.query().size());
  }

  /**
   * Testing the object in the category.
   */
  @Test
  void findObjectInCategory() {
    PricingCategory pricingCategory = new PricingCategory();
    pricingCategory.query().add(new Pricing("a", 1));
    assertNotNull(pricingCategory.findObject("a"));
  }

  /**
   * Testing the object not in the category.
   */
  @Test
  void findNullInCategory() {
    PricingCategory pricingCategory = new PricingCategory();
    assertNull(pricingCategory.findObject("a"));
  }

  /**
   * Testing the repository search in mapper.
   */
  @Test
  void mapperQueryRepo() {
    PricingMapper pricingMapper = new PricingMapper();
    assertNotNull(pricingMapper.queryRepository());
  }

  /**
   * Testing the correct update for the repository search in mapper.
   */
  @Test
  void mapperQueryRepoSize() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("f", 1));
    assertEquals(1, pricingMapper.queryRepository().size());
  }

  /**
   * Testing the category search in mapper.
   */
  @Test
  void mapperQueryCate() {
    PricingMapper pricingMapper = new PricingMapper();
    assertNotNull(pricingMapper.queryCategory());
  }

  /**
   * Testing the correct update for the category search in mapper.
   */
  @Test
  void mapperQueryCateSize() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("f", 1));
    assertEquals(1, pricingMapper.queryCategory().size());
  }

  /**
   * Testing the lease operation.
   */
  @Test
  void mapperAddLease() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    pricingMapper.addLease(new Pricing("a", 1));
    assertEquals(2, pricingMapper.queryRepository().size());
  }

  /**
   * Testing the lease operation of the correct content.
   */
  @Test
  void mapperAddLeaseExact() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    assertEquals("a", pricingMapper.queryRepository().get(0).getName());
  }

  /**
   * Testing the object being correctly added in the category.
   */
  @Test
  void mapperAddObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    assertTrue(pricingMapper.addObject(new Pricing("a", 1)));
  }

  /**
   * Testing the object in the category cannot be duplicate.
   */
  @Test
  void mapperAddObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("a", 1));
    assertFalse(pricingMapper.addObject(new Pricing("a", 1)));
  }

  /**
   * Testing the object be removed successfully.
   */
  @Test
  void mapperRemoveObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("a", 1));
    assertTrue(pricingMapper.removeObject("a"));
  }

  /**
   * Testing the object cannot be removed without existence.
   */
  @Test
  void mapperRemoveObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.removeObject("a"));
  }

  /**
   * Testing the object can be modified successfully.
   */
  @Test
  void mapperModifyObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.modifyObject("a", new Pricing("a", 1)));
  }

  /**
   * Testing the object cannot be modified without existence.
   */
  @Test
  void mapperModifyObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addObject(new Pricing("a", 1));
    assertTrue(pricingMapper.modifyObject("a", new Pricing("b", 2)));
  }

  /**
   * Testing the lease can be removed successfully.
   */
  @Test
  void mapperRemoveLeaseSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    assertTrue(pricingMapper.removeLease(0));
  }

  /**
   * Testing the lease cannot be removed without existence.
   */
  @Test
  void mapperRemoveLeaseFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.removeLease(0));
  }

  /**
   * Testing the object can be leased successfully.
   */
  @Test
  void mapperLeaseObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    assertTrue(pricingMapper.leaseObject(0));
  }

  /**
   * Testing the object cannot be leased without existence.
   */
  @Test
  void mapperLeaseObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.leaseObject(0));
  }

  /**
   * Testing the object can be returned successfully.
   */
  @Test
  void mapperReturnObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    pricingMapper.addLease(new Pricing("a", 1));
    pricingMapper.leaseObject(0);
    assertTrue(pricingMapper.returnObject(0));
  }

  /**
   * Testing the object cannot be returned without existence.
   */
  @Test
  void mapperReturnObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    assertFalse(pricingMapper.returnObject(0));
  }

  /**
   * Testing the customer can query the repository correctly.
   */
  @Test
  void customerQueryRepo() {
    PricingMapper pricingMapper = new PricingMapper();
    Customer customer = new Customer(pricingMapper);
    assertEquals(pricingMapper.queryRepository(),customer.queryRepository());
  }

  /**
   * Testing the customer can get the repository.
   */
  @Test
  void customerQueryRepoSize(){
    PricingMapper pricingMapper = new PricingMapper();
    Customer customer = new Customer(pricingMapper);
    assertEquals(0,customer.queryRepository().size());
  }

  /**
   * Testing the customer can get the category correctly.
   */
  @Test
  void customerQueryCate() {
    PricingMapper pricingMapper = new PricingMapper();
    Customer customer = new Customer(pricingMapper);
    assertEquals(pricingMapper.queryCategory(),customer.queryCategory());
  }

  /**
   * Testing the customer can get the category.
   */
  @Test
  void customerQueryCateSize(){
    PricingMapper pricingMapper = new PricingMapper();
    Customer customer = new Customer(pricingMapper);
    assertEquals(0,customer.queryCategory().size());
  }

  /**
   * Testing the lease class can get the repository correctly.
   */
  @Test
  void leaseQueryRepo() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    assertEquals(pricingMapper.queryRepository(),lease.queryLease());
  }

  /**
   * Testing the lease class can get the repository.
   */
  @Test
  void leaseQueryRepoSize(){
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    assertEquals(0,lease.queryLease().size());
  }

  /**
   * Testing the asset can get the category correctly.
   */
  @Test
  void assetQueryCate() {
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    assertEquals(pricingMapper.queryCategory(),asset.queryCategory());
  }

  /**
   * Testing the asset can get the category.
   */
  @Test
  void assetQueryCateSize(){
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    assertEquals(0,asset.queryCategory().size());
  }

  /**
   * Testing the lease class can add leases.
   */
  @Test
  void leaseAddLease() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    lease.addLease(new Pricing("a", 1));
    assertEquals(2, lease.queryLease().size());
  }

  /**
   * Testing the lease class can add a lease correctly.
   */
  @Test
  void leaseAddLeaseExact() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    assertEquals("a", lease.queryLease().get(0).getName());
  }

  /**
   * Testing the asset can add a object in the category.
   */
  @Test
  void assetAddObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    assertTrue(asset.addObject(new Pricing("a", 1)));
  }

  /**
   * Testing the asset cannot add the duplicate object.
   */
  @Test
  void assetAddObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    asset.addObject(new Pricing("a", 1));
    assertFalse(asset.addObject(new Pricing("a", 1)));
  }

  /**
   * Testing the asset can remove the object.
   */
  @Test
  void assetRemoveObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    asset.addObject(new Pricing("a", 1));
    assertTrue(asset.removeObject("a"));
  }

  /**
   * Testing the asset cannot remove the object without existence.
   */
  @Test
  void assetRemoveObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    assertFalse(asset.removeObject("a"));
  }

  /**
   * Testing the asset cannot modify the object without existence.
   */
  @Test
  void assetModifyObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    assertFalse(asset.modifyObject("a", new Pricing("a", 1)));
  }

  /**
   * Testing the asset can modify the object correctly.
   */
  @Test
  void assetModifyObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    Asset asset = new Asset(pricingMapper);
    asset.addObject(new Pricing("a", 1));
    assertTrue(asset.modifyObject("a", new Pricing("b", 2)));
  }

  /**
   * Testing the lease class can remove the lease correctly.
   */
  @Test
  void leaseRemoveLeaseSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    assertTrue(lease.removeLease(0));
  }

  /**
   * Testing the lease class cannot remove the lease without existence.
   */
  @Test
  void leaseRemoveLeaseFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    assertFalse(lease.removeLease(0));
  }

  /**
   * Testing the lease class can lease an object correctly.
   */
  @Test
  void leaseLeaseObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    assertTrue(lease.leaseObject(0));
  }

  /**
   * Testing the lease class cannot lease the object without existence.
   */
  @Test
  void leaseLeaseObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    assertFalse(lease.leaseObject(0));
  }

  /**
   * Testing the lease class can return an object successfully.
   */
  @Test
  void leaseReturnObjectSuccess() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    lease.addLease(new Pricing("a", 1));
    lease.leaseObject(0);
    assertTrue(lease.returnObject(0));
  }

  /**
   * Testing the lease class cannot return the object without existence.
   */
  @Test
  void leaseReturnObjectFailure() {
    PricingMapper pricingMapper = new PricingMapper();
    Lease lease = new Lease(pricingMapper);
    assertFalse(lease.returnObject(0));
  }
}
