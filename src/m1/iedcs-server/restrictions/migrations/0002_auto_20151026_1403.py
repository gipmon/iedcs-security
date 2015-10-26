# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('restrictions', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='restriction',
            old_name='value',
            new_name='restrictionFunction',
        ),
        migrations.RemoveField(
            model_name='restriction',
            name='booleanValue',
        ),
    ]
