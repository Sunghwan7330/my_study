import requests
from bs4 import BeautifulSoup as bs
import pandas as pd


def add_finance_info(writer, sheet_name, url_str):
    data_res = []

    page = requests.get(url_str)
    soup = bs(page.text, "html.parser")    
    elements = soup.select('#contentarea > div:nth-child(5) > table > tbody > tr > td.name ')

    for index, element in enumerate(elements, 1):
        data_arr = []
        data_arr.append(element.text)
        href_url = element.a.get('href')
        data_arr.append(href_url[21:])

        url_naver_finace ='https://finance.naver.com'
        finace_page = requests.get(url_naver_finace + href_url)
        finace_soup = bs(finace_page.text, "html.parser")
        finace_elements = finace_soup.select('#summary_info')

        for idx, elem in enumerate(finace_elements, 1):
            data_arr.append(elem.text)

        data_res.append(data_arr)

        df = pd.DataFrame(data_res)
        print(df)

        df.to_excel(writer, sheet_name=sheet_name, index=False, header=False)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    writer = pd.ExcelWriter('/Users/shanechoi/Desktop/REF/123.xlsx', engine='xlsxwriter')

    res = add_finance_info(writer, "sheet1", "https://finance.naver.com/sise/sise_group_detail.nhn?type=theme&no=30")
    res = add_finance_info(writer, "sheet2", "https://finance.naver.com/sise/sise_group_detail.nhn?type=theme&no=486")
    writer.save()
