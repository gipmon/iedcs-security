# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('books', '0002_book_original_file'),
    ]

    operations = [
        migrations.AlterUniqueTogether(
            name='book',
            unique_together=set([('name', 'production_date', 'author')]),
        ),
    ]
