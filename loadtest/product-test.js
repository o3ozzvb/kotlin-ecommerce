import http from 'k6/http';

const BASE = 'http://localhost:8080';

export const options = {
    // 100명이 20번씩
    /*scenarios: {
        hundred_x20: {
            executor: 'per-vu-iterations', // VU마다 딱 정해진 횟수 실행
            vus: 100,                       // 동시 100명
            iterations: 20,                 // 1명당 20회 → 총 2000회
            maxDuration: '10m',
        },
    },*/

    // 1명에서 100명까지 점진 증가
  /*  stages: [
        { duration: '30s', target: 1 },    // 1명으로 시작
        { duration: '2m',  target: 100 },  // 2분 동안 100명까지 증가
        { duration: '1m',  target: 100 },  // 1분 유지
        { duration: '30s', target: 0 },    // 정리
    ],*/

    // 초당 고정 RPS로
    /*scenarios: {
        rps_200: {
            executor: 'constant-arrival-rate',
            rate: 200,             // 초당 200 요청
            timeUnit: '1s',
            duration: '2m',        // 2분간
            preAllocatedVUs: 50,   // 미리 확보할 VU
            maxVUs: 200,           // 필요시 늘릴 최대 VU
        },
    },*/

    // 스파이크
    stages: [
        { duration: '10s', target: 10 },   // 워밍업
        { duration: '10s', target: 200 },  // 10초만에 200으로 급증 (스파이크)
        { duration: '1m',  target: 200 },  // 유지
        { duration: '30s', target: 0 },    // 다운
    ],
};

export default function () {
    const url = `${BASE}/products?sortBy=like_desc`;

    http.get(url);
}