# 리팩터링 2판 1장 예시 1
# javascript 코드를 python으로 변경하여 구현

def statement(invoice, plays):
    totalAmount = 0
    volumeCredits = 0
    result = '청구 내역 (고객명: {})\n'.format(invoice['customer'])
    creditFormat = ',.2f'
    for perf in invoice['performances']:
        play = plays[perf['playID']]
        thisAmount = 0
        if play['type'] == 'tragedy': #비극
            thisAmount = 40000;
            if perf['audience'] > 30:
                thisAmount += 1000 * (perf['audience'] - 30)
        elif play['type'] == "comedy": #비극
            thisAmount = 30000
            if perf['audience'] > 20:
                thisAmount += 10000 + 500 * (perf['audience'] - 20)
            thisAmount += 300 * perf['audience'];
        else:
            return '알 수 없는 장르: %s' % play['type']

        # 포인트를 적립한다.
        volumeCredits += max(perf['audience'] - 30, 0);

        # 희극 관객 5명마다 추가 포인트를 제공한다.
        if "comedy" == play['type']:
            volumeCredits += perf['audience'] // 5
        # 청구 내역을 출력한다.
        result += ' {}: ${} ({}석)\n'.format(play['name'], format(thisAmount//100, creditFormat), perf['audience'])
        totalAmount += thisAmount;

    result += '총액: ${}\n'.format(format(totalAmount//100, creditFormat));
    result += '적립 포인트: {}점\n'.format(volumeCredits);
    return result;

def main():
    invoice = {}
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
    plays = {
            "hamlet": {"name": "Hamlet", "type": "tragedy"},
            "as-like": {"name": "As You Like It", "type": "comedy"},
            "othello": {"name": "Othello", "type": "tragedy"}
    }
    print(statement(invoice, plays))
    return

if __name__ == '__main__':
    main()
