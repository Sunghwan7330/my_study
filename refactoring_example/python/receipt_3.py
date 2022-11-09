# 리팩터링 2판 1장 예시 3
# statement, amountFor 에서 play 제거

def playFor(aPerformance):
    plays = {
    "hamlet": {"name": "Hamlet", "type": "tragedy"},
    "as-like": {"name": "As You Like It", "type": "comedy"},
    "othello": {"name": "Othello", "type": "tragedy"}
    }
    return plays[aPerformance['playID']]

def amountFor(aPerformance): # 값이 바뀌지 않는 변수는 파라메타로 전달
    result = 0  # 명확한 이름으로 변경 

    type = playFor(aPerformance)['type']
    if type == 'tragedy': #비극
        result = 40000
        if aPerformance['audience'] > 30:
            result += 1000 * (aPerformance['audience'] - 30)
    elif type == "comedy": #비극
        result = 30000
        if aPerformance['audience'] > 20:
            result += 10000 + 500 * (aPerformance['audience'] - 20)
        result += 300 * aPerformance['audience']
    else:
        return -1
    
    return result  # 함수 안에서 값이 바뀌는 변수 반환


def statement(invoice):
    totalAmount = 0
    volumeCredits = 0
    result = '청구 내역 (고객명: {})\n'.format(invoice['customer'])
    creditFormat = ',.2f'
    for perf in invoice['performances']:
        thisAmount = amountFor(perf)

        # 포인트를 적립한다.
        volumeCredits += max(perf['audience'] - 30, 0)

        # 희극 관객 5명마다 추가 포인트를 제공한다.
        if "comedy" == playFor(perf)['type']:
            volumeCredits += perf['audience'] // 5
        # 청구 내역을 출력한다.
        result += ' {}: ${} ({}석)\n'.format(playFor(perf)['name'], format(thisAmount//100, creditFormat), perf['audience'])
        totalAmount += thisAmount

    result += '총액: ${}\n'.format(format(totalAmount//100, creditFormat))
    result += '적립 포인트: {}점\n'.format(volumeCredits)
    return result

def main():
    invoice = {
        "customer": "BigCo",
        "performances": [
            {
                "playID": "hamlet",
                "audience": 55
            },
            {
                "playID": "as-like",
                "audience": 35
            },
            {
                "playID": "othello",
                "audience": 40
            }
        ]
    }

    print(statement(invoice))
    return

if __name__ == '__main__':
    main()
