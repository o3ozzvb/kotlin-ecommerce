# 요구사항 정리
## 1. 상품 / 브랜드
### [목록 조회]
1. 시나리오 (사용자의 관점)
    - 상품 목록을 조회할 수 있다.

2. 기능 명세 (시스템의 관점)
    - 목적 : 사용자는 페이지별 상품 정보를 조회할 수 있어야 한다.
    - Actor (주체) : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자가 조회 조건(상품명, 브랜드 등)에 따른 상품 목록 조회를 요청한다.
        - 시스템은 조회 조건에 알맞은 상품 목록을 반환(페이징, 정렬 포함)한다.
          . 정렬 : 최신순, 가격순, 좋아요순
    - 시스템 동작 (낙관적)
        - DB 에서 조건에 해당하는 상품 목록 조회
        - 상품 목록 정보 제공
    - 예외 (비관적)
        - 조회 조건에 해당하는 상품 목록이 없으면 빈 리스트 반환

### [상품 상세 조회]
1. 시나리오 (사용자의 관점)
    - 상품 상세 페이지를 조회할 수 있다.
    - 브랜드별 상품을 조회할 수 있다.

2. 기능 명세 (시스템의 관점)
    - 목적 : 사용자는 상품의 상세 정보를 조회할 수 있어야 한다.
    - Actor (주체) : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자가 상품의 상세 조회를 요청한다.
        - 시스템은 상품의 상세 정보(상품명, 상품 가격, 상품 좋아요 수, 로그인 사용자라면 좋아요 여부)를 반환한다.
    - 시스템 동작 (낙관적)
        - DB 에서 상품 상세 정보 조회
        - 상품 상세 정보 제공
    - 예외 (비관적)
        - 상품이 존재하지 않으면 예외 반환 -> 404

### [브랜드 정보 조회]
1. 시나리오
    - 브랜드 정보를 조회할 수 있다.

2. 기능 명세
    - 목적 : 사용자는 브랜드의 정보를 조회할 수 있어야 한다.
    - Actor (주체) : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자가 브랜드 조회를 요청한다.
        - 시스템은 브랜드의 정보(브랜드명, 브랜드의 상품 목록)를 반환한다.
    - 시스템 동작 (낙관적)
        - DB 에서 브랜드 정보 조회
        - 브랜드 정보 제공
    - 예외 (비관적)
        - 브랜드가 존재하지 않으면 예외 반환 -> 404

## 2. 좋아요
### [좋아요 등록]
1. 시나리오
    - 사용자는 상품에 좋아요를 등록할 수 있다.

2. 기능 명세
    - 목적 : 사용자는 상품에 좋아요를 등록할 수 있어야 한다.
    - Actor : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자가 상품 목록 또는 상품 상세 페이지에서 상품에 좋아요를 등록한다.
        - DB에 상품 테이블 좋아요 count +1 저장된다.
        - DB에 사용자-좋아요 데이터가 저장된다.
    - 시스템 동작 (낙관적)
        - X-USER-ID (헤더값) 검증
    - 예외
        - X-USER-ID에 대한 검증이 실패했을 경우 -> 401 (Unauthorized)
        - 좋아요 등록에 실패 했을 경우 -> 500

### [좋아요 취소]
1. 시나리오
    - 사용자는 상품 좋아요를 취소할 수 있다.

2. 기능 명세
    - 목적 : 사용자는 상품에 좋아요를 취소할 수 있어야 한다.
    - Actor : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자가 상품 목록 또는 상품 상세 페이지에서 상품에 좋아요를 취소한다.
        - 좋아요 했던 상품인지 확인
        - DB에 상품 테이블 좋아요 count -1 저장된다.
        - DB에 사용자-좋아요 데이터가 저장된다.
    - 시스템 동작 (낙관적)
        - X-USER-ID (헤더값) 검증
    - 예외
        - X-USER-ID에 대한 검증이 실패했을 경우 -> 401 (Unauthorized)
        - 좋아요 취소에 실패 했을 경우 -> 500

### [내가 좋아요한 상품 목록 조회]
1. 시나리오
    - 사용자는 자신이 좋아요한 상품 목록을 조회할 수 있다.

2. 기능 명세
    - 목적 : 사용자는 자신이 좋아요한 상품 목록을 조회할 수 있어야 한다.
    - Actor : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자는 자신이 좋아요한 상품 목록 조회를 요청한다.
        - DB에서 사용자가 좋아요한 상품 목록을 조회한다.
        - 시스템은 사용자가 좋아요한 상품 목록을 반환한다. (페이징 처리, 정렬(최신순))
    - 시스템 동작
        - X-USER-ID (헤더값) 검증
    - 예외
        - X-USER-ID에 대한 검증이 실패했을 경우 -> 401 (Unauthorized)
        - 데이터가 없을 경우 빈 리스트를 반환한다.

## 3. 주문/결제
### [주문 요청]
1. 시나리오
    - 사용자는 주문을 생성하고, 결제할 수 있다.

2. 기능 명세
    - 목적 : 사용자는 상품을 선택 그에 대한 주문을 생성할 수 있어야 한다.
    - Actor : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자는 상품을 선택하고, 선택한 상품 목록에 대한 주문을 요청한다.
        - DB에 주문 정보(상품 리스트)가 저장된다.
        - 주문한 상품의 재고가 차감된다.
        - 사용자는 보유한 쿠폰을 주문에 적용할 수 있다.
        - 주문 금액에 대한 포인트가 차감된다.
        - DB에 결제 데이터가 저장된다.
        - DB에 차감된 포인트 데이터와 이력이 저장된다.
        - 주문 요청이 완료되면 외부 플랫폼으로 알림 메시지가 발송된다.
    - 시스템 동작 (낙관적)
        - X-USER-ID (헤더값) 검증
        - 재고 > 0 검증
        - 포인트 잔액 검증
    - 예외
        - X-USER-ID에 대한 검증이 실패했을 경우 -> 401 (Unauthorized)
        - 주문 생성에 실패 했을 경우 -> 500

### [주문 목록 조회]
1. 시나리오
    - 사용자는 자신의 주문 목록을 조회할 수 있다.

2. 기능 명세
    - 목적 : 사용자는 자신의 주문 목록을 조회할 수 있어야 한다.
    - Actor : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자는 주문 목록 조회를 요청한다.
        - DB에서 사용자의 주문을 조회한다.
        - 시스템은 사용자의 주문 목록(페이징 처리, 정렬(최신순))을 반환한다.
    - 시스템 동작 (낙관적)
        - X-USER-ID (헤더값) 검증
    - 예외
        - X-USER-ID에 대한 검증이 실패했을 경우 -> 401 (Unauthorized)
        - 주문 데이터가 없을 경우 빈 리스트를 반환한다.

### [주문 상세 조회]
1. 시나리오
    - 사용자는 자신의 주문 상세 정보를 조회할 수 있다.

2. 기능 명세
    - 목적 : 사용자는 자신의 주문 상세 정보를 조회할 수 있어야 한다.
    - Actor : 일반 사용자 (대고객)
    - 주요 시나리오
        - 사용자는 주문 상세 정보 조회를 요청한다.
        - DB에서 사용자의 주문 상세 정보를 조회한다.
        - 시스템은 사용자의 주문 상세 정보(주문 상품 목록, 주문 요청 일시 등)를 반환한다.
    - 시스템 동작 (낙관적)
        - X-USER-ID (헤더값) 검증
    - 예외
        - X-USER-ID에 대한 검증이 실패했을 경우 -> 401 (Unauthorized)
        - 주문 데이터가 없을 경우 예외를 반환한다. -> 404
