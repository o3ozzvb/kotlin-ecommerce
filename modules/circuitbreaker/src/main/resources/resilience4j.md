# Resilience4j Configuration

## CircuitBreaker  
### slidingWindowType
- COUNT_BASED: 최근 N개의 호출을 기준으로 판단 (기본값)
- TIME_BASED: 최근 N초간의 호출을 기준으로 판단
### slidingWindowSize
- 집계되는 슬라이딩 윈도우 크기 (기본값: 100)
### failureRateThreshold
- 실패율 임계값을 백분율로 설정. (기본값: 50)
- 해당 값을 넘어갈 시 Circuit Breaker는 Open 상태로 전환되며, 이때부터 호출을 차단.
### waitDurationInOpenState
- OPEN 상태 유지기간 (기본값: 60초)
- 서킷브레이커가 OPEN된 후 HALF_OPEN으로 전환되기까지의 대기 시간
### permittedNumberOfCallsInHalfOpenState
- HALF_OPEN 상태일 때, OPEN/CLOSE 여부를 판단하기 위해 허용할 호출 수 (기본값: 10)
- 중요한 시스템일수록 작게 설정
### minimumNumberOfCalls
- 실패율을 계산할 최소한의 호출 수 (기본값: 100)
### recordExceptions
- 실패로 기록할 Exception 리스트 (기본값: empty)
### ignoreExceptions
- 실패나 성공으로 기록하지 않을 Exception 리스트 (기본값: empty)
### slowCallDurationThreshold
- 호출에 소요되는 시간이 설정한 임계치보다 길면 느린 호출로 계산 (기본값: 60000ms(60초))
### slowCallRateThreshold
- 임계값을 백분율로 설정. (기본값: 100)
- 호출에 걸리는 시간이 `slowCallDurationThreshold`보다 길면 느린 호출로 간주, 해당 값을 넘어갈 경우 OPEN 상태로 전환되며 호출을 차단.


## Retry
### maxAttempts
- 최초 호출을 포함한 총 재시도 횟수 (기본값: 3)
### waitDuration
- 각 재시도 사이의 대기 시간 (기본값: 500ms)
### intervalFunction
- 재시도 간격 함수 타입
- exponentialBackoff(지수 백오프), fixedDelay(고정 지연)
### exponentialBackoffMultiplier
- 지수 백오프 배수 (기본값: 1.5)
- 각 재시도마다 대기 시간이 배수만큼 증가 (1초 -> 2초 -> 4초 -> 8초)
### randomizationFactor
- 무작위화 요소 (기본값: 0.5)
- Thundering Herd 문제 방지를 위한 지터(jitter) 추가
### retryExceptions
- 실패로 기록되는 블랙리스트 예외 (기본값: empty)
- empty일 경우 모든 예외 클래스를 재시도 한다.
### retryOnResultPredicate 
- 기본값: (numOfAttempts,Either<throwable, result) -> waitDuration
- 반환되는 결과에 따라 retry 여부를 결정하는 filter, true를 반환하면 retry하고 false로 반환하면 retry 하지 않음.
### failAfterMaxAttempts
- 설정한 maxAttempts 만큼 재시도한 이후에도 여전히 `retryOnResultPredicate`를 통과하지 못했을 때 `MaxRetriesExceededException` 발생을 활성화/비활성화하는 boolean (기본값: false)

# Resilience4j Core Modules
## Priority (우선순위)
###  Retry ( CircuitBreaker ( RateLimiter ( TimeLimiter ( BulkHead ( TargetFunction ) ) ) ) )
- 위와 같은 우선순위로 모듈이 적용되며, Retry 모듈이 가장 마지막에 적용됨