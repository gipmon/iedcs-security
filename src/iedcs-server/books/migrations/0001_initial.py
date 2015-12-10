# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings
import django.core.validators
import uuid


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Book',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('identifier', models.CharField(default=uuid.uuid4, unique=True, max_length=128)),
                ('name', models.CharField(max_length=128, validators=[django.core.validators.MinLengthValidator(1)])),
                ('production_date', models.DateField()),
                ('author', models.CharField(max_length=128, validators=[django.core.validators.MinLengthValidator(1)])),
                ('original_file', models.FileField(upload_to=b'')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='OrderBook',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('book', models.ForeignKey(to='books.Book')),
                ('buyer', models.ForeignKey(to=settings.AUTH_USER_MODEL)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.AlterUniqueTogether(
            name='orderbook',
            unique_together=set([('book', 'buyer')]),
        ),
        migrations.AlterUniqueTogether(
            name='book',
            unique_together=set([('name', 'production_date', 'author')]),
        ),
    ]
