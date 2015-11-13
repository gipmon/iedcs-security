from django.core.management.base import BaseCommand
from ...models import Book, Restriction, BookRestrictions
from ...restrictions import methods


class Command(BaseCommand):
    help = 'This script inserts the restriction\nUsage: \n'
    help += 'python manage.py restriction add restrict_book book_identifier restriction_name\n'
    help += 'python manage.py restriction add restriction restriction_name restrictionFunction cause\n'
    help += 'python manage.py restriction rm restrict_book book_identifier restriction_name\n'
    help += 'python manage.py restriction list books'
    # help += 'python manage.py restriction rm restriction restriction_name'

    def __init__(self):
        super(Command, self).__init__()

    def handle(self, *args, **options):
        if len(args) == 4 and args[0] == "add" and args[1] == "restrict_book":
            try:
                book = Book.objects.get(identifier=args[2])
                restriction = Restriction.objects.get(aliasKey=args[3])
                BookRestrictions.objects.create(book=book, restriction=restriction)
            except Book.DoesNotExist:
                print "The book doesn't exists!"
            except Restriction.DoesNotExist:
                print "The restriction doesn't exists!"
            print args
        elif len(args) == 5 and args[0] == "add" and args[1] == "restriction":
            if args[3] not in methods:
                print "Please code the restriction function in the file restrictions.py!"
                return

            Restriction.objects.create(aliasKey=args[2], restrictionFunction=args[3], cause=args[4])
            print args
        elif len(args) == 4 and args[0] == "rm" and args[1] == "restrict_book":
            try:
                book = Book.objects.get(identifier=args[2])
                restriction = Restriction.objects.get(aliasKey=args[3])
                bkr = BookRestrictions.objects.get(book=book, restriction=restriction)
                bkr.delete()
            except Book.DoesNotExist:
                print "The book doesn't exists!"
            except Restriction.DoesNotExist:
                print "The restriction doesn't exists!"
            except BookRestrictions.DoesNotExist:
                print "Book restriction doesn't exist!"
            print args
        elif len(args) == 2 and args[0] == 'list' and args[1] == 'books':
            for book in Book.objects.all():
                print book.identifier + " " + book.name
        # elif len(args) == 3 and args[0] == "rm" and args[1] == "restriction":
        #        print "rm restriction"
        else:
            print self.help
