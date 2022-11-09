import pandas as pd
import json

CONFIG_FILE = 'config.json'
KEY_EXCEL_FILE = 'excel_file'
KEY_SEARCH_FILE = 'search_list_file'
KEY_SELECT_SHEET = 'select_sheet'
KEY_COLUMN = 'key_column'
KET_IS_INTEGER = 'is_key_integer'
KEY_VALUE_INDEX_ARR = 'value_index_arr'
KEY_WRITE_FILE = 'write_file'
KEY_DEBUG_MESSAGE = 'debug_message'

debug_flag = False;

def readSearchList(filename, isIngeter=False):
    res = []
    f = open(filename, 'r')
    while True:
        line = f.readline()
        if not line: break
        if isIngeter:
            res.append(int(line))
        else:
            res.append(line.replace('\n', ''))
    f.close()
    return res

def readConfig():
    with open(CONFIG_FILE) as f:
        conf = json.load(f)

    return conf

def print_debug(str):
    if debug_flag:
        print(str)

def getResultFromExcel(excel_file, sheet_name, key_col, search_list, write_file_name):
    res = ''
    print_debug('* Load Excel')
    df = pd.read_excel(excel_file, sheet_name=[sheet_name])
    print_debug('  - Load Sueecss')
    sheet = df[sheet_name]

    sheet[sheet[key_col].isin(search_list)].to_excel(write_file_name)

    return res

def main():
    conf = readConfig()
    global debug_flag
    debug_flag = conf[KEY_DEBUG_MESSAGE]
    getResultFromExcel(conf[KEY_EXCEL_FILE],
                       conf[KEY_SELECT_SHEET],
                       conf[KEY_COLUMN],
                       readSearchList(conf[KEY_SEARCH_FILE], isIngeter=conf[KET_IS_INTEGER]),
                       conf[KEY_WRITE_FILE]
                       )
    print_debug('* Finish!')
    return


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()