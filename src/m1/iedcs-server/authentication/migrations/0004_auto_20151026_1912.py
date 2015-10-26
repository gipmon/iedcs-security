# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0003_auto_20151026_1910'),
    ]

    operations = [
        migrations.AlterField(
            model_name='account',
            name='user_data',
            field=models.ForeignKey(to='authentication.UserCollectedData'),
            preserve_default=True,
        ),
    ]
