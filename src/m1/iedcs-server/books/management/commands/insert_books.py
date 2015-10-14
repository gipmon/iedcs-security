from django.core.management.base import BaseCommand
from ...models import Book
from django.core.files.storage import default_storage
from iedcs.settings import BASE_DIR


class Command(BaseCommand):
    help = 'This script inserts the scripts for you'

    def __init__(self):
        super(Command, self).__init__()

    def handle(self, *args, **options):
        # http://www.gutenberg.org/

        books = [{'name': 'Cyclopedia of Telephony & Telegraphy Vol. 2', 'production_date': '2010-08-15', 'author': 'Kempster Miller et. al.', 'original_file': 'pg33437.txt'},
                 {'name': 'Delco Radio Owner\'s Manual Model 633', 'production_date': '2010-02-26', 'author': 'Delco-Remy Division', 'original_file': 'pg31407.txt'},
                 {'name': 'Development of the Phonograph at Alexander Graham Bell\'s Volta Laboratory', 'production_date': '2009-09-27', 'author': 'Leslie J. Newville', 'original_file': 'pg30112.txt'},
                 {'name': 'IBM 1401 Programming Systems', 'production_date': '2008-12-09', 'author': 'Anonymous', 'original_file': 'pg27468.txt'},
                 {'name': 'A treatise on domestic economy, Rev. ed.', 'production_date': '2007-06-14', 'author': 'Beecher, Catharine Esther', 'original_file': 'pg21829.txt'},
                 {'name': 'Furnishing the Home of Good Taste', 'production_date': '2005-01-28', 'author': 'Throop, Lucy Abbot', 'original_file': 'pg14824.txt'},
                 {'name': 'Guide to hotel housekeeping', 'production_date': '2011-01-25', 'author': 'Mary E. Palmer', 'original_file': 'pg35066.txt'},
                 {'name': 'Household organization', 'production_date': '2010-08-18', 'author': 'Florence Caddy', 'original_file': 'pg34097.txt'},
                 {'name': 'Mrs. Beeton\'s Household Management', 'production_date': '2003-11-19', 'author': 'Mrs. Isabella Beeton', 'original_file': 'pg10136.txt'},
                 {'name': 'The House in Good Taste', 'production_date': '2005-01-17', 'author': 'Elsie de Wolfe', 'original_file': 'pg14715.txt'},
                 {'name': 'The American frugal housewife, 12th edition', 'production_date': '2004-09-18', 'author': 'Lydia M. Child', 'original_file': 'pg13493.txt'},
                 {'name': 'The cost of shelter', 'production_date': '2004-05-16', 'author': 'Ellen H. Richards', 'original_file': 'pg12366.txt'},
                 {'name': 'How to Prepare and Serve a Meal and Interior Decoration', 'production_date': '2005-01-01', 'author': 'Lillian B. Lansdown', 'original_file': 'pg7350.txt'},
                 {'name': 'Practical Suggestions for Mother and Housewife', 'production_date': '2005-09-01', 'author': 'Marion Mills Miller', 'original_file': 'pg8996.txt'},
                 {'name': 'The American Woman\'s Home; or Principles of Domestic Science', 'production_date': '2004-09-01', 'author': 'Catherine E. Beecher and Harriet Beecher Stowe', 'original_file': 'pg6598.txt'},
                 {'name': 'Ontario Teachers\' Manuals: Household Management', 'production_date': '2008-02-20', 'author': 'Ministry of Education', 'original_file': 'pg24656.txt'},
                 {'name': 'Ontario Teachers\' Manuals: Household Science in Rural Schools ', 'production_date': '2007-02-10', 'author': 'Ministry of Education Ontario', 'original_file': 'pg20557.txt'}]

        try:
            for book in books:
                Book.objects.create(name=book['name'], production_date=book['production_date'], author=book['author'],
                                    original_file=default_storage.path(BASE_DIR+'/media/books/'+book['original_file']))
        except Exception, e:
            print "The books are already inserted!"
            # print e.message
