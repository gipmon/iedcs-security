# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('orders', '0002_auto_20151013_2058'),
    ]

    operations = [
        migrations.RenameField(
            model_name='order',
            old_name='order_identifier',
            new_name='identifier',
        ),
    ]
