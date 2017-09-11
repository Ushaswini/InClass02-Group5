namespace InClass02_DiscountsAPI.Migrations
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<InClass02_DiscountsAPI.Models.DiscountsAPIContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(InClass02_DiscountsAPI.Models.DiscountsAPIContext context)
        {
            context.Discounts.AddOrUpdate(
                x=>x.Id,
                new Models.Discount { Id = 1, DiscountValue = 10, Name = "Pineapple", Photo = "https://productphotos.blob.core.windows.net/images/pineapple.png", Price = 1.18M, RegionId = 1},
                new Models.Discount { Id = 2, DiscountValue = 10, Name = "Croissants", Photo = "https://productphotos.blob.core.windows.net/images/croissants.png", Price = 2.79M, RegionId = 2 },
                new Models.Discount { Id = 3, DiscountValue = 20, Name = "Brach's Jelly Beans", Price = 2.21M, RegionId = 2 },
                new Models.Discount { Id = 4, DiscountValue = 20, Name = "Dial Soap",Photo= "https://productphotos.blob.core.windows.net/images/croissants.png", Price = 2.99M, RegionId = 3 },
                new Models.Discount { Id = 5, DiscountValue = 10, Name = "Oranges",Photo= "https://productphotos.blob.core.windows.net/images/oranges.png", Price = 0.89M, RegionId = 1 },
                new Models.Discount { Id = 6, DiscountValue = 15, Name = "Scotch Blite Sponges", Photo= "https://productphotos.blob.core.windows.net/images/scotch-brite-sponges.png", Price = 5.89M, RegionId = 3 },
                new Models.Discount { Id = 7, DiscountValue = 15, Name = "Fresh Express Lettuce",Photo= "https://productphotos.blob.core.windows.net/images/lettuce.jpg", Price = 3.69M, RegionId = 1 },
                new Models.Discount { Id = 8, DiscountValue = 15, Name = "Coca cola", Photo= "https://productphotos.blob.core.windows.net/images/coca-cola.png", Price = 6.99M, RegionId = 2 },
                new Models.Discount { Id = 9, DiscountValue = 10, Name = "Gastorade",Photo= "https://productphotos.blob.core.windows.net/images/gatorade.png", Price = 3.89M, RegionId = 2 },
                new Models.Discount { Id = 10, DiscountValue = 10, Name = "Organix Conditioner",Photo= "https://productphotos.blob.core.windows.net/images/organix-conditioner.png", Price = 13.46M, RegionId = 3 },
                new Models.Discount { Id = 11, DiscountValue = 10, Name = "Spinach", Price = 1.23M,Photo= "https://productphotos.blob.core.windows.net/images/spinach.png", RegionId = 1 },
                new Models.Discount { Id = 12, DiscountValue = 15, Name = "Cranberry cocktail",Photo= "https://productphotos.blob.core.windows.net/images/cranberry-cocktail.png", Price = 1.89M, RegionId = 2 },
                new Models.Discount { Id = 13, DiscountValue = 20, Name = "Milk",Photo= "https://productphotos.blob.core.windows.net/images/milk.jpg", Price = 10.5M, RegionId = 2 },
                new Models.Discount { Id = 14, DiscountValue = 15, Name = "HI-C Fruit Punch",Photo= "https://productphotos.blob.core.windows.net/images/hi-c-fruit-punch.png", Price = 4.67M, RegionId = 2 },
                new Models.Discount { Id = 15, DiscountValue = 10, Name = "Nectarines", Photo= "https://productphotos.blob.core.windows.net/images/fresh-nectarines.png", Price = 3.67M, RegionId = 1 },
                new Models.Discount { Id = 16, DiscountValue = 10, Name = "Fresh seedless whole watermelon",Photo= "https://productphotos.blob.core.windows.net/images/watermellon.jpg", Price = 6.99M, RegionId = 1 },
                new Models.Discount { Id = 17, DiscountValue = 15, Name = "US weekly",Photo= "https://productphotos.blob.core.windows.net/images/us-weekly.png", Price = 4.99M, RegionId = 3 }
                );

            context.Regions.AddOrUpdate(
                x => x.Id,
                new Models.Region { Id=1, RegionName="produce"},
                new Models.Region { Id=2,RegionName="grocery"},
                new Models.Region { Id=3,RegionName="lifestyle"}
                );
        }
    }
}
