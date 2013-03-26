#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:     
#
# Author:      Anish
#
# Created:     26 03 2013
# Copyright:   (c) Anish 2013
# Licence:     <your licence>
#-------------------------------------------------------------------------------
#!/usr/bin/env python

import json
import getpass

bookmarkspath_fname = '../bookmarks-path.txt'
trainurls_fname = '../train-urls.csv'
testurls_fname = '../test-urls.csv'

def extract_urls(json_data, folder):
    if json_data['type'] == 'folder':
        folder = json_data['name']
        if folder.lower() == 'ignore': return []
        url_data = []
        for child in json_data['children']:
            url_data += extract_urls(child, folder)
    else:
        url_data = [(json_data['url'], folder)]
    return url_data

def main():
    with open(bookmarkspath_fname, 'r') as fin:
        bookmarks_fname = fin.read().strip()
        bookmarks_fname = bookmarks_fname.replace('%username%', getpass.getuser())
    with open(bookmarks_fname, 'r') as fin:
        json_data = json.loads(fin.read().replace('\n', ' '))
        train_urls = extract_urls(json_data['roots']['bookmark_bar'], 'roots')
        test_urls = extract_urls(json_data['roots']['other'], 'roots')
    with open(trainurls_fname, 'w') as fout:
        for url, label in train_urls:
            fout.write('{};{}\n'.format(url, label))
    with open(testurls_fname, 'w') as fout:
        for url, ignoreme in test_urls:
            fout.write('{}\n'.format(url))

if __name__ == '__main__':
    main()
