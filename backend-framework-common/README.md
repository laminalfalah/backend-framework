# Backend Framework Common

### Standard Paging Request
For Paging, we can use `Filter` class for web request, and `Filter` class for web response.
We also already implement `FilterArgumentResolver` to support argument injection on controller,
so we don't need to parse paging request manually.

In the url we can use standard paging :

```
GET /api/users?page=1&item_per_page=100

GET /api/users?page=2&item_per_page=50

GET /api/users?page=2
```

In the `Filter` we also can give sorting information, and it will automatically inject to `Filter`.

```
GET /api/users?page=2&sort_by=id:asc,first_name:asc,created_at:desc

GET /api/users?page=2&sort_by=id,first_name,created_at:desc
```

```properties
backend.paging.default-field=createdAt
backend.paging.default-field-direction=desc

backend.paging.default-size=50
backend.paging.max-size-per-page=100
```