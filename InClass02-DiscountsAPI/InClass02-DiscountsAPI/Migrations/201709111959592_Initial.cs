namespace InClass02_DiscountsAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Discounts",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        DiscountValue = c.Int(nullable: false),
                        Name = c.String(),
                        Photo = c.String(),
                        Price = c.Decimal(nullable: false, precision: 18, scale: 2),
                        RegionId = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.Regions",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        RegionName = c.String(),
                    })
                .PrimaryKey(t => t.Id);
            
        }
        
        public override void Down()
        {
            DropTable("dbo.Regions");
            DropTable("dbo.Discounts");
        }
    }
}
