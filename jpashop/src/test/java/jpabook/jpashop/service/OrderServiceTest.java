package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    //상품 주문
    @Test
    public void 상품주문()throws Exception{
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount=2;


        Long orderId=orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문 상태는 Order");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 동일해야 한다");
        assertEquals(10000*orderCount,getOrder.getTotalPrice(),"주문 가격은 가격 * 수량이다");
        assertEquals(8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");

    }

    //상품주문_재고수량초과
    @Test
    public void 상품주문_재고수량초과()throws Exception{
        Member member = createMember();
        Item item=createBook("시골 JPA", 10000, 10);
        int orderCount=11;

        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        }, "재고 수량 예외가 발생해야 한다.");

    }

    //주문취소
    @Test
    public void 주문취소()throws Exception{
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);
        int orderCount=2;
        Long order_Id = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(order_Id);

        //then
        Order getOrder = orderRepository.findOne(order_Id);
        assertEquals(OrderStatus.CANCEL,getOrder.getStatus(),"주문 취소시 상태는 Cancel이다");
        assertEquals(10,item.getStockQuantity(),"주문이 취소된 상품은 그만큼 재고가 늘어나야한다.");


    }


    private Book createBook(String name, int price, int stockQuantity) {
        Book book=new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member=new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

}