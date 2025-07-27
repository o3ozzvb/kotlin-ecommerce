# 시퀀스 다이어그램
## 1. 좋아요 등록
```mermaid
sequenceDiagram
    actor Users
    participant PLC as ProductLikeController
    participant PLF as ProductLikeFacade
    participant US as UserService
    participant LS as LikeService
	participant PS as ProductService

    Users->>+PLC: POST /products/{id}/like
	PLC->>+PLF: 좋아요 등록 요청
	
	PLF->>+US: 사용자 조회
	alt 사용자가 존재하지 않을 경우
	US-->>PLF: throw 401 Unauthorized
	PLF-->>PLC: 401 Unauthorized
	PLC-->>Users: 401 Unauthorized
	else 사용자가 존재할 경우
	US-->>-PLF: 사용자 정보 반환
	end
	
	PLF->>+LS: 좋아요 조회  
	alt 좋아요가 존재하지 않을 경우
	LS->>LS: like(productId, userId)
	LS->>+PS: like(productId)
	PS-->>-LS: 좋아요 수 증가
	LS-->>PLF: 좋아요 등록
	else 좋아요가 존재하면
	LS-->>-PLF: Do Nothing
	end
	
    PLF-->>-PLC: 좋아요 등록 성공
    PLC-->>-Users: 200 OK
```

## 2. 좋아요 취소
```mermaid
sequenceDiagram
    actor Users
    participant PLC as ProductLikeController
    participant PLF as ProductLikeFacade
    participant US as UserService
    participant LS as LikeService
    participant PS as ProductService

    Users->>+PLC: POST /products/{id}/unlike
    PLC->>+PLF: 좋아요 등록 요청
    PLF->>+US: 사용자 조회
    alt 사용자가 존재하지 않을 경우
        US-->>PLF: throw 401 Unauthorized
        PLF-->>PLC: 401 Unauthorized 전파
        PLC-->>Users: 401 Unauthorized
    else 사용자가 존재할 경우
        US-->>-PLF: 사용자 정보 반환
    end

    PLF->>+LS: 좋아요 조회
    alt 좋아요가 존재하지 않을 경우
        LS-->>PLF: Do Nothing
    else 좋아요가 존재하면
        LS->>LS: unlike(productId, userId)
        LS->>+PS: unlike(productId)
        PS-->>-LS: 좋아요 수 감소
        LS-->>-PLF: 좋아요 취소
    end
    PLF->>-PLC: 좋아요 취소 성공
    PLC->>-Users: 200 OK
```

## 3. 주문 요청
```mermaid
sequenceDiagram
	actor Users
	participant OF as OrderFacade
	participant OS as OrderService
	participant PS as ProductService
	participant CS as CouponService

    Users->>+OF: POST /orders
    
    Note over OF, PS: 1. 재고 검증
    OF->>+PS: 재고 검증 요청
    PS->>PS: 재고 확인
	
	alt 재고 >= 주문 수량
	PS-->>PS: consumeStock(quantity)
	PS-->>OF: 재고 차감
	else 재고 < 주문 수량
	PS-->>-OF: 409 Confilct
	OF-->>Users: 409 Confilct
	end

    Note over OF, CS: 2. 쿠폰 검증 및 적용
	alt 쿠폰 적용 요청 시
	OF->>+CS: 사용자 쿠폰 검증
		alt 쿠폰이 사용 가능하다면
		CS->>CS: useCoupon(issuedCouponId)
		CS-->>OF: 쿠폰 사용 완료
		OF->>+OS: applyCoupon(coupon)
		OS-->>-OF: 쿠폰 적용 완료
		else 쿠폰 사용 불가하다면
		CS-->>-OF: 409 Confilct
		Note over OF, PS: 롤백 - 차감된 재고 복원
		OF->>PS: restoreStock(quantity)
		OF-->>Users: 409 Conflict
		end
    end
    
    Note over OF, OS: 3. 주문 정보 저장
    OF->>+OS: save(order)
    OS->>OS: 주문 정보 저장
    OS-->>-OF: 주문 저장 완료
    OF-->>-Users: 201 Created (주문 생성 성공)
	
```
## 4. 결제 요청
```mermaid
sequenceDiagram
    actor Users
    participant PF as PaymentFacade
    participant PMS as PaymentService
    participant PS as PointService
    participant Platform

    Users->>+PF: 결제 요청
    
    Note over PF, PS: 1. 포인트 잔액 검증
    PF->>+PS: 포인트 잔액 확인 (userId, amount)
    PS->>PS: 잔액 확인
    
	alt 잔액 >= 결제 금액
	    PS-->>-PF: 잔액 확인 완료
	    
	    Note over PF, PS: 2. 포인트 차감
        PF->>+PS: 포인트 차감 요청(userId, amount)
        PS->>PS: 잔액 차감
        PS-->>-PF: 포인트 차감 완료

        PF->>+PMS: save(paymentLog)
        PMS->>PMS: 결제 정보 저장
        PMS-->>-PF: 결제 정보 저장 완료
        PF-)Platform: 알림 메시지 전송
        PF-->>Users: 200 OK (결제 성공)
	else 잔액 < 결제 금액
        PS-->>PF: exception
        PF-->>-Users: 409 Conflict
	end
    
 
```