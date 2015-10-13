# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('files', '0002_auto_20151013_1957'),
    ]

    operations = [
        migrations.AlterUniqueTogether(
            name='file',
            unique_together=set([('buyer', 'book')]),
        ),
    ]
