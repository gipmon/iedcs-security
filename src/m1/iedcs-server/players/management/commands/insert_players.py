from django.core.management.base import BaseCommand
from django.core.files.storage import default_storage
from iedcs.settings.base import BASE_DIR
import os
from django.core.files.storage import Storage
from ...models import PlayerVersion


class Command(BaseCommand):
    help = 'This script inserts the players keys and information to the database'

    def __init__(self):
        super(Command, self).__init__()

    def handle(self, *args, **options):

        path_player = default_storage.path(BASE_DIR+'/media/player')
        folders = os.listdir(path_player)


        try:
            for folder in folders:
                folder_path = path_player+ '/' + folder
                if os.path.isdir(folder_path):
                    files = os.listdir(folder_path)
                    private_key_path = ""
                    public_key_path = ""
                    for file in files:
                        if file == 'public.key':
                            public_key_path = folder_path + '/public.key'

                        if file == 'private.key':
                            private_key_path = folder_path + '/private.key'

                    PlayerVersion.objects.create(version=folder, public_key=public_key_path, private_key=private_key_path)
        except Exception, e:
            print "The player version is already inserted!"
            print e.message
